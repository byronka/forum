package com.renomad.forum;

import com.renomad.minum.logging.TestLogger;
import com.renomad.minum.state.Constants;
import com.renomad.minum.state.Context;
import com.renomad.minum.web.FullSystem;
import com.renomad.minum.web.FunctionalTesting;
import com.renomad.minum.web.StatusLine;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;

import static com.renomad.minum.testing.TestFramework.*;
import static com.renomad.minum.web.StatusLine.StatusCode.CODE_200_OK;


public class AppTest
{

    private static Context context;
    private static TestLogger logger;
    private static FullSystem fullSystem;
    private static FunctionalTesting ft;

    /**
     * Initialize a full system startup before all tests
     */
    @BeforeClass
    public static void init() {
        // build useful state
        context = buildTestingContext("AppTest");
        Constants constants = context.getConstants();
        logger = (TestLogger) context.getLogger();

        // initialize the whole system - this basically kicks off
        // the Minum web engine
        fullSystem = new FullSystem(context);
        fullSystem.start();

        // register the endpoints in our engine
        Main.registerEndpoints(fullSystem.getWebFramework());

        // build an object useful for fast integration testing
        ft = new FunctionalTesting(context, constants.hostName, constants.serverPort);
    }

    /**
     * Cleanly shut down the system after the tests finish
     */
    @AfterClass
    public static void cleanup() throws IOException {
        fullSystem.shutdown();
        context.getLogger().stop();
        context.getExecutorService().shutdownNow();
    }

    /**
     * This will cause a header to be output in the logs before each test, like:
     * +------------------------------------------------------------------------+
     * | TEST 1: testGettingStaticResource(com.renomad.forum.AppTests)          |
     * +------------------------------------------------------------------------+
     */
    @Rule(order = Integer.MIN_VALUE)
    public TestWatcher watchman = new TestWatcher() {
        protected void starting(Description description) {
            logger.test(description.toString());
        }
    };


    /**
     * Confirm we get what is expected for the index.html page, the
     * page presented to the user when they hit http://localhost:8080/index.html
     */
    @Test
    public void testIndexHappyPath() {
        // execute an HTTP GET request for the "index" endpoint
        FunctionalTesting.TestResponse testResponse = ft.get("index.html");

        assertEquals(testResponse.statusLine().status(), CODE_200_OK);
    }

    /**
     * If the user requests http://localhost:8080/, the system will
     * redirect their browser to index.html
     */
    @Test
    public void testIndexRedirectFromEmpty() {
        FunctionalTesting.TestResponse testResponseEmpty = ft.get("");

        assertEquals(testResponseEmpty.statusLine().status(), StatusLine.StatusCode.CODE_303_SEE_OTHER);
        assertEquals(testResponseEmpty.headers().valueByKey("location").getFirst(), "/index.html");
    }

    /**
     * If the user requests http://localhost:8080/index, the system will
     * redirect their browser to index.html
     */
    @Test
    public void testIndexRedirectFromIndex() {
        FunctionalTesting.TestResponse testResponseEmpty = ft.get("index");

        assertEquals(testResponseEmpty.statusLine().status(), StatusLine.StatusCode.CODE_303_SEE_OTHER);
        assertEquals(testResponseEmpty.headers().valueByKey("location").getFirst(), "/index.html");
    }


}

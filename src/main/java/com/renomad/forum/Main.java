package com.renomad.forum;

import com.renomad.minum.web.FullSystem;
import com.renomad.minum.web.Response;
import com.renomad.minum.web.WebFramework;

import static com.renomad.minum.web.RequestLine.Method.GET;
import static com.renomad.minum.web.RequestLine.Method.POST;

public class Main {

    public static void main(String[] args) {
        // Start the application
        FullSystem fs = FullSystem.initialize();

        // Register the endpoints
        registerEndpoints(fs.getWebFramework());

        // the instruction pointer of the main thread will wait here
        fs.block();
    }

    /**
     * This method handles registering the endpoints that this
     * application will serve.  By splitting it out to its own
     * method, the testing becomes much easier.  See AppTest.java
     */
    public static void registerEndpoints(WebFramework webFramework) {
        webFramework.registerPath(GET, "", request -> Response.redirectTo("/index.html"));
        webFramework.registerPath(GET, "index", request -> Response.redirectTo("/index.html"));
        webFramework.registerPath(POST, "hello", request -> Response.redirectTo("/index.html"));
    }
}

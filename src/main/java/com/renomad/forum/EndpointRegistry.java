package com.renomad.forum;

import com.renomad.minum.web.WebFramework;

import static com.renomad.minum.web.RequestLine.Method.GET;
import static com.renomad.minum.web.RequestLine.Method.POST;

/**
 * Contains all the registrations of endpoints.  Also a place where
 * necessary one-time-only initializations are made, such as databases
 * and widely-accessed objects.
 */
public class EndpointRegistry {

    /**
     * This method handles registering the endpoints that this
     * application will serve.  By splitting it out to its own
     * method, the testing becomes much easier.  See AppTest.java
     */
    public static void registerEndpoints(WebFramework webFramework) {
        var homepage = new HomePage();
        var userRegistrationPage = new UserRegistrationPage();
        var userLoginPage = new UserLoginPage();

        // homepage endpoints
        webFramework.registerPath(GET, "", request -> homepage.indexHandleGet(request));
        webFramework.registerPath(GET, "index", request -> homepage.indexHandleGet(request));

        // user login
        webFramework.registerPath(GET, "login", request -> userLoginPage.handleGetToLogin(request));
        webFramework.registerPath(POST, "login", request -> userLoginPage.handlePostToLogin(request));

        // user registration
        webFramework.registerPath(GET, "register", request -> userRegistrationPage.handleGetToRegister(request));
        webFramework.registerPath(POST, "register", request -> userRegistrationPage.handlePostToRegister(request));
    }
}

package com.renomad.forum;

import com.renomad.minum.web.FullSystem;

import static com.renomad.forum.EndpointRegistry.registerEndpoints;

public class Main {

    public static void main(String[] args) {
        // Start the application
        FullSystem fs = FullSystem.initialize();

        // Register the endpoints
        registerEndpoints(fs.getWebFramework());

        // the instruction pointer of the main thread will wait here
        fs.block();
    }

}

package org.solent.examples.com600.mcride.web.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApp extends ResourceConfig {

    public RestApp() {
        packages("org.solent.examples.com600.mcride.web.rest");
    }
}

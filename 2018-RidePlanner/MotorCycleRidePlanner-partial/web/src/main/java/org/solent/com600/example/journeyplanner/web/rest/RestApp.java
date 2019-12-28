package org.solent.com600.example.journeyplanner.web.rest;

import org.solent.com600.example.journeyplanner.web.rest.*;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApp extends ResourceConfig {

    public RestApp() {
        packages("org.solent.com600.example.journeyplanner.web.rest");
    }
}

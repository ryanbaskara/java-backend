package com.ryanbaskara.learning.presentation.route;

import com.ryanbaskara.learning.presentation.handler.InstrumentHandler;
import io.vertx.rxjava3.ext.web.Router;

public class InstrumentRoute {
    public static void configure(Router router, InstrumentHandler handler) {
        router.get("/health").handler(handler::getHealth);
    }
}

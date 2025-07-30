package com.ryanbaskara.learning.presentation.handler;

import io.vertx.rxjava3.ext.web.RoutingContext;

public class InstrumentHandler {
    public void getHealth(RoutingContext ctx) {
        ctx.response().putHeader("Content-Type", "application/json").end("OK");
    }
}

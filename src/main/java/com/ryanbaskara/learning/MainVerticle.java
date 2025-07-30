package com.ryanbaskara.learning;

import com.ryanbaskara.learning.presentation.handler.InstrumentHandler;
import com.ryanbaskara.learning.presentation.route.InstrumentRoute;

import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);

        InstrumentHandler instrumentHandler = new InstrumentHandler();
        InstrumentRoute.configure(router, instrumentHandler);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888)
                .subscribe(http -> System.out.println("Server started"));
    }
}
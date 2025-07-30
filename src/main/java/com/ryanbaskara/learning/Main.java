package com.ryanbaskara.learning;

import io.vertx.rxjava3.core.Vertx;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle())
                .subscribe(id -> System.out.println("Deployed Verticle with ID: " + id),
                        err -> System.err.println("Deployment failed: " + err.getMessage()));
    }
}

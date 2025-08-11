package com.ryanbaskara.learning.presentation.handler;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.ext.web.RoutingContext;

import java.util.List;

public class ResponseHandler {
    public static void success(RoutingContext ctx, Object object) {
        ctx.response()
                .setStatusCode(200)
                .putHeader("Content-Type", "application/json")
                .end(new JsonObject().put("data", object).encode());
    }

    public static void success(RoutingContext ctx, List<Object> objects) {
        JsonArray result = new JsonArray();
        objects.forEach(obj -> result.add(JsonObject.mapFrom(obj)));
        ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(result.encode());
    }
}

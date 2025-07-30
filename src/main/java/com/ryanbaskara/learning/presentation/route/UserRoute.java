package com.ryanbaskara.learning.presentation.route;

import com.ryanbaskara.learning.presentation.handler.UserHandler;
import io.vertx.rxjava3.ext.web.Router;

public class UserRoute {
    public static void configure(Router router, UserHandler handler) {
        router.get("/users").handler(handler::getUsers);
    }
}

package com.ryanbaskara.learning.presentation.handler;

import com.ryanbaskara.learning.domain.usecase.GetUsersUseCase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.ext.web.RoutingContext;

public class UserHandler {
    private final GetUsersUseCase getUsersUseCase;

    public UserHandler(GetUsersUseCase getUsersUseCase) {
        this.getUsersUseCase = getUsersUseCase;
    }

    public void getUsers(RoutingContext ctx) {
        getUsersUseCase.execute()
                .subscribe(
                        users -> {
                            JsonArray result = new JsonArray();
                            users.forEach(user -> result.add(JsonObject.mapFrom(user)));
                            ctx.response()
                                    .putHeader("content-type", "application/json")
                                    .end(result.encode());
                        },
                        error -> {
                            ctx.response()
                                    .setStatusCode(500)
                                    .end(new JsonObject().put("error", error.getMessage()).encode());
                        }
                );
    }
}

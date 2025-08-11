package com.ryanbaskara.learning.presentation.handler;

import com.ryanbaskara.learning.presentation.dto.response.ErrorDetail;
import com.ryanbaskara.learning.presentation.dto.response.ErrorResponse;
import com.ryanbaskara.learning.presentation.exception.ApiException;
import io.vertx.core.json.Json;
import io.vertx.rxjava3.ext.web.Router;
import io.vertx.rxjava3.ext.web.RoutingContext;

import java.util.List;

public class GlobalExceptionHandler {
    public static void handle(RoutingContext ctx) {
        Throwable failure = ctx.failure();

        int status = 500;
        String message = "Internal Server Error";

        if (failure instanceof ApiException ex) {
            status = ex.getStatusCode();
            message = ex.getMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse(
                message,
                List.of(new ErrorDetail(message, status))
        );

        ctx.response()
                .setStatusCode(status)
                .putHeader("Content-Type", "application/json")
                .end(Json.encode(errorResponse));
    }
}

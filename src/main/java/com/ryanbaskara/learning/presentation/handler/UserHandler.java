package com.ryanbaskara.learning.presentation.handler;

import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.usecase.CreateUserUseCase;
import com.ryanbaskara.learning.domain.usecase.GetUsersUseCase;
import com.ryanbaskara.learning.domain.usecase.UpdateUserUseCase;
import com.ryanbaskara.learning.presentation.dto.CreateUserRequest;
import com.ryanbaskara.learning.presentation.dto.UpdateUserRequest;
import io.reactivex.rxjava3.disposables.Disposable;
import io.vertx.rxjava3.ext.web.RoutingContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class UserHandler {
    private final GetUsersUseCase getUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final Validator validator;

    public UserHandler(GetUsersUseCase getUsersUseCase,
                       CreateUserUseCase createUserUseCase,
                       UpdateUserUseCase updateUserUseCase) {
        this.getUsersUseCase = getUsersUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void getUsers(RoutingContext ctx) {
        Disposable disposable = getUsersUseCase.execute()
                .subscribe(
                        users -> {
                            ResponseHandler.success(ctx, users);
                        },
                        ctx::fail
                );
    }

    public void createUser(RoutingContext ctx) {
        CreateUserRequest request = ctx.body().asPojo(CreateUserRequest.class);

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            ctx.response()
                    .setStatusCode(400)
                    .end("Validation error: " + errors);
            return;
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        createUserUseCase.execute(user)
                .subscribe(
                        savedUser -> {
                            ResponseHandler.success(ctx, savedUser);
                        },
                        ctx::fail
                );
    }

    public void updateUser(RoutingContext ctx) {
        long id = Integer.parseInt(ctx.pathParam("id"));
        UpdateUserRequest request = ctx.body().asPojo(UpdateUserRequest.class);
        User user = new User();
        user.setId(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        updateUserUseCase.execute(user).subscribe(
                newUser -> {
                    ResponseHandler.success(ctx, newUser);
                },
                ctx::fail
        );
    }
}

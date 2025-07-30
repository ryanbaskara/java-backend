package com.ryanbaskara.learning;

import com.ryanbaskara.learning.application.usecase.GetUsersUseCaseImpl;
import com.ryanbaskara.learning.domain.usecase.GetUsersUseCase;
import com.ryanbaskara.learning.infrastructure.config.DatabaseConfig;
import com.ryanbaskara.learning.infrastructure.repository.UserRepositoryImpl;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import com.ryanbaskara.learning.presentation.handler.InstrumentHandler;
import com.ryanbaskara.learning.presentation.handler.UserHandler;
import com.ryanbaskara.learning.presentation.route.InstrumentRoute;
import com.ryanbaskara.learning.presentation.route.UserRoute;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.ext.web.Router;
import io.vertx.rxjava3.jdbcclient.JDBCPool;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);

        JDBCPool pool = DatabaseConfig.connect(vertx);
        UserRepository userRepository = new UserRepositoryImpl(pool);
        GetUsersUseCase getUsersUseCase = new GetUsersUseCaseImpl(userRepository);

        InstrumentHandler instrumentHandler = new InstrumentHandler();
        InstrumentRoute.configure(router, instrumentHandler);

        UserHandler userHandler = new UserHandler(getUsersUseCase);
        UserRoute.configure(router, userHandler);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888)
                .subscribe(http -> System.out.println("Server started"));
    }
}
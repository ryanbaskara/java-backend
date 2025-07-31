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
import io.github.cdimascio.dotenv.Dotenv;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.ext.web.Router;
import io.vertx.rxjava3.mysqlclient.MySQLPool;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);

        MySQLPool pool = DatabaseConfig.createMysqlPool(vertx);
        UserRepository userRepository = new UserRepositoryImpl(pool);
        GetUsersUseCase getUsersUseCase = new GetUsersUseCaseImpl(userRepository);

        InstrumentHandler instrumentHandler = new InstrumentHandler();
        InstrumentRoute.configure(router, instrumentHandler);

        UserHandler userHandler = new UserHandler(getUsersUseCase);
        UserRoute.configure(router, userHandler);

        Dotenv env = Dotenv.load();
        int serverPort = Integer.parseInt(env.get("SERVER_PORT"));
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(serverPort)
                .subscribe(http -> System.out.printf("Server started at localhost:%d%n", serverPort));
    }
}
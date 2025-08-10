package com.ryanbaskara.learning;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ryanbaskara.learning.application.usecase.CreateUserUseCaseImpl;
import com.ryanbaskara.learning.application.usecase.GetUsersUseCaseImpl;
import com.ryanbaskara.learning.application.usecase.UpdateUserUseCaseImpl;
import com.ryanbaskara.learning.domain.usecase.CreateUserUseCase;
import com.ryanbaskara.learning.domain.usecase.GetUsersUseCase;
import com.ryanbaskara.learning.domain.usecase.UpdateUserUseCase;
import com.ryanbaskara.learning.infrastructure.config.DatabaseConfig;
import com.ryanbaskara.learning.infrastructure.repository.UserRepositoryImpl;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import com.ryanbaskara.learning.presentation.handler.InstrumentHandler;
import com.ryanbaskara.learning.presentation.handler.UserHandler;
import com.ryanbaskara.learning.presentation.route.InstrumentRoute;
import com.ryanbaskara.learning.presentation.route.UserRoute;
import io.github.cdimascio.dotenv.Dotenv;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.ext.web.Router;
import io.vertx.rxjava3.ext.web.handler.BodyHandler;
import io.vertx.rxjava3.mysqlclient.MySQLPool;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // Register support for java.time.* types for jackson
        ObjectMapper mapper = DatabindCodec.mapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
        prettyMapper.registerModule(new JavaTimeModule());
        prettyMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        MySQLPool pool = DatabaseConfig.createMysqlPool(vertx);
        UserRepository userRepository = new UserRepositoryImpl(pool);
        GetUsersUseCase getUsersUseCase = new GetUsersUseCaseImpl(userRepository);
        CreateUserUseCase createUserUseCase = new CreateUserUseCaseImpl(userRepository);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCaseImpl(userRepository);

        InstrumentHandler instrumentHandler = new InstrumentHandler();
        InstrumentRoute.configure(router, instrumentHandler);

        UserHandler userHandler = new UserHandler(getUsersUseCase, createUserUseCase, updateUserUseCase);
        UserRoute.configure(router, userHandler);

        Dotenv env = Dotenv.load();
        int serverPort = Integer.parseInt(env.get("SERVER_PORT"));
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(serverPort)
                .subscribe(http -> System.out.printf("Server started at localhost:%d%n", serverPort));
    }
}
package com.ryanbaskara.learning;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.ryanbaskara.learning.presentation.handler.GlobalExceptionHandler;
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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        setupJacksonTime();

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
        router.route().failureHandler(GlobalExceptionHandler::handle);

        Dotenv env = Dotenv.load();
        int serverPort = Integer.parseInt(env.get("SERVER_PORT"));
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(serverPort)
                .subscribe(http -> System.out.printf("Server started at localhost:%d%n", serverPort));
    }

    private void setupJacksonTime() {
        // Register support for java.time.* types for jackson
        ObjectMapper mapper = DatabindCodec.mapper();
        formatTime(mapper);

        ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
        formatTime(prettyMapper);
    }

    private void formatTime(ObjectMapper mapper) {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        mapper.configOverride(LocalDateTime.class)
                .setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        mapper.configOverride(java.util.Date.class)
                .setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        mapper.configOverride(java.sql.Timestamp.class)
                .setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
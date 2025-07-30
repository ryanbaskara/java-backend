package com.ryanbaskara.learning.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;

import io.vertx.jdbcclient.JDBCConnectOptions;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.jdbcclient.JDBCPool;
import io.vertx.sqlclient.PoolOptions;

public class DatabaseConfig {
    public static JDBCPool connect(Vertx vertx) {
        Dotenv env = Dotenv.load();
        String host = env.get("DB_MYSQL_HOST");
        String port = env.get("DB_MYSQL_PORT");
        String username = env.get("DB_MYSQL_USERNAME");
        String password = env.get("DB_MYSQL_PASSWORD");
        String db = env.get("DB_MYSQL_DATABASE");

        return JDBCPool.pool(vertx, new JDBCConnectOptions()
                        .setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", host, port, db))
                        .setUser(username)
                        .setPassword(password),
                new PoolOptions().setMaxSize(5)
        );
    }
}

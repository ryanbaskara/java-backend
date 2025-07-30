package com.ryanbaskara.learning.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;

import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

public class DatabaseConfig {
    public static MySQLPool createMysqlPool(Vertx vertx) {
        Dotenv env = Dotenv.load();
        String host = env.get("DB_MYSQL_HOST");
        String port = env.get("DB_MYSQL_PORT");
        String username = env.get("DB_MYSQL_USERNAME");
        String password = env.get("DB_MYSQL_PASSWORD");
        String db = env.get("DB_MYSQL_DATABASE");

        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(Integer.parseInt(port))
                .setHost(host)
                .setDatabase(db)
                .setUser(username)
                .setPassword(password);

        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

        return MySQLPool.pool(vertx, connectOptions, poolOptions);
    }
}

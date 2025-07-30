package com.ryanbaskara.learning.infrastructure.repository;

import io.vertx.rxjava3.sqlclient.Row;
import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;
import io.vertx.rxjava3.jdbcclient.JDBCPool;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final JDBCPool client;

    public UserRepositoryImpl(JDBCPool client) {
        this.client = client;
    }

    @Override
    public Single<List<User>> getUsers() {
        return client
                .preparedQuery("SELECT id, name, email FROM users")
                .rxExecute()
                .map(rows -> {
                    List<User> users = new ArrayList<>();
                    for (Row row : rows) {
                        users.add(new User(
                                row.getInteger("id"),
                                row.getString("name"),
                                row.getString("email")
                        ));
                    }
                    return users;
                });
    }
}

package com.ryanbaskara.learning.infrastructure.repository;

import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;
import io.vertx.rxjava3.mysqlclient.MySQLPool;
import io.vertx.rxjava3.sqlclient.Row;
import io.vertx.rxjava3.sqlclient.RowSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

    private final MySQLPool client;

    public UserRepositoryImpl(MySQLPool client) {
        this.client = client;
    }

    @Override
    public Single<List<User>> getUsers() {
        return client
                .query("SELECT id, name, email FROM users")
                .rxExecute()
                .map(rowSet -> mapToUserList(rowSet));
    }
    private List<User> mapToUserList(RowSet<Row> rows) {
        List<User> users = new ArrayList<>();
        for (Row row : rows) {
            User user = new User();
            user.setId(row.getInteger("id"));
            user.setName(row.getString("name"));
            user.setEmail(row.getString("email"));
            users.add(user);
        }
        return users;
    }
}

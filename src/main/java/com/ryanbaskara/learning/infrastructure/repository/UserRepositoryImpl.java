package com.ryanbaskara.learning.infrastructure.repository;

import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.vertx.rxjava3.mysqlclient.MySQLClient;
import io.vertx.rxjava3.mysqlclient.MySQLPool;
import io.vertx.rxjava3.sqlclient.Row;
import io.vertx.rxjava3.sqlclient.RowSet;
import io.vertx.rxjava3.sqlclient.Tuple;

import java.time.format.DateTimeFormatter;
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
                .query("SELECT * FROM users")
                .rxExecute()
                .map(this::mapToUserList);
    }

    @Override
    public Maybe<User> getUserByID(long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        return client.preparedQuery(query)
                .rxExecute(Tuple.of(id))
                .flatMapMaybe(rowSet -> {
                    if (!rowSet.iterator().hasNext()) {
                        return Maybe.empty();
                    }

                    Row row = rowSet.iterator().next();
                    User user = mapRowToUser(row);
                    return Maybe.just(user);
                });
    }

    @Override
    public Single<User> createUser(User user) {
        String query = "INSERT INTO users (name, email, created_at, updated_at) VALUES (?, ?, ?, ?)";

        return client.preparedQuery(query)
                .rxExecute(Tuple.of(
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        user.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .map(rows -> {
                    user.setId(rows.property(MySQLClient.LAST_INSERTED_ID));
                    return user;
                });
    }

    @Override
    public Single<Boolean> updateUser(User user) {
        String query = "UPDATE users SET name = ?, email = ?, updated_at = ? WHERE id = ?";
        return client.preparedQuery(query)
                .rxExecute(Tuple.of(
                        user.getName(),
                        user.getEmail(),
                        user.getUpdatedAt(),
                        user.getId()
                ))
                .map(rows -> rows.rowCount() > 0);
    }

    private List<User> mapToUserList(RowSet<Row> rows) {
        List<User> users = new ArrayList<>();
        for (Row row : rows) {
            User user = mapRowToUser(row);
            users.add(user);
        }
        return users;
    }

    private User mapRowToUser(Row row) {
        User user = new User();
        user.setId(row.getLong("id"));
        user.setName(row.getString("name"));
        user.setEmail(row.getString("email"));
        user.setCreatedAt(row.getLocalDateTime("created_at"));
        user.setUpdatedAt(row.getLocalDateTime("updated_at"));
        return user;
    }
}

package com.ryanbaskara.learning.domain.repository;

import com.ryanbaskara.learning.domain.model.User;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface UserRepository {
    Single<List<User>> getUsers();
}

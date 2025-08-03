package com.ryanbaskara.learning.domain.usecase;

import com.ryanbaskara.learning.domain.model.User;
import io.reactivex.rxjava3.core.Single;

public interface CreateUserUseCase {
    Single<User> execute(User user);
}

package com.ryanbaskara.learning.domain.usecase;

import com.ryanbaskara.learning.domain.model.User;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface GetUsersUseCase {
    Single<List<User>> execute();
}

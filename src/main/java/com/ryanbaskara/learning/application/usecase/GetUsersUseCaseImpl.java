package com.ryanbaskara.learning.application.usecase;

import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import com.ryanbaskara.learning.domain.usecase.GetUsersUseCase;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public class GetUsersUseCaseImpl implements GetUsersUseCase {

    private final UserRepository userRepository;

    public GetUsersUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<List<User>> execute() {
        return userRepository.getUsers();
    }
}

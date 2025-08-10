package com.ryanbaskara.learning.application.usecase;

import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import com.ryanbaskara.learning.domain.usecase.GetUsersUseCase;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.List;
import java.util.stream.Collectors;

public class GetUsersUseCaseImpl implements GetUsersUseCase {

    private final UserRepository userRepository;

    public GetUsersUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<List<User>> execute() {
        Disposable disposable = userRepository.getUsers()
                .map(users ->
                        users.stream()
                                .map(user -> user.getName().toUpperCase())
                                .collect(Collectors.toList())
                ).subscribe(
                        names -> System.out.println("Nama-nama: " + names),
                        error -> error.printStackTrace()
                );

        return userRepository.getUsers();
    }
}

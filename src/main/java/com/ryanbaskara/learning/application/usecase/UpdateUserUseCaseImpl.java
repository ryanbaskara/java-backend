package com.ryanbaskara.learning.application.usecase;

import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import com.ryanbaskara.learning.domain.usecase.UpdateUserUseCase;
import io.reactivex.rxjava3.core.Single;

import java.time.LocalDateTime;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<User> execute(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.getUserByID(user.getId())
                .flatMap(existingUser -> {
                    User newUser = existingUser;
                    newUser.setEmail(user.getEmail());
                    newUser.setName(user.getName());
                   return userRepository.updateUser(newUser)
                           .flatMap(success -> {
                               if (success) {
                                   return Single.just(newUser);
                               } else {
                                   return Single.error(new IllegalStateException("Update failed"));
                               }
                           });
                });
    }
}

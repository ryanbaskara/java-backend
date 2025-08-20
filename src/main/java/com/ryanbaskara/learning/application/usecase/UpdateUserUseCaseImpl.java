package com.ryanbaskara.learning.application.usecase;

import com.ryanbaskara.learning.domain.event.EventPublisher;
import com.ryanbaskara.learning.domain.exception.UserNotFoundException;
import com.ryanbaskara.learning.domain.model.User;
import com.ryanbaskara.learning.domain.repository.UserRepository;
import com.ryanbaskara.learning.domain.usecase.UpdateUserUseCase;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.time.LocalDateTime;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public UpdateUserUseCaseImpl(UserRepository userRepository, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Single<User> execute(User user) {
        return userRepository.getUserByID(user.getId())
                .switchIfEmpty(Single.error(new UserNotFoundException(user.getId())))
                .flatMap(existingUser -> {
                    existingUser.setEmail(user.getEmail());
                    existingUser.setName(user.getName());
                    existingUser.setUpdatedAt(LocalDateTime.now());
                   return userRepository.updateUser(existingUser)
                           .flatMap(success -> {
                               if (!success) {
                                   return Single.error(new IllegalStateException("Update failed"));
                               }
                               eventPublisher.publish("users", String.valueOf(user.getId()), user.toString());
                               return Single.just(existingUser);
                           });
                });
    }
}

package com.ryanbaskara.learning.domain.exception;

import com.ryanbaskara.learning.presentation.exception.ApiException;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(long id) {
        super(String.format("User with id: %d not found", id), 404);
    }
}

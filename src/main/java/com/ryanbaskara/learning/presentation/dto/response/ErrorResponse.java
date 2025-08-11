package com.ryanbaskara.learning.presentation.dto.response;

import java.util.List;

public class ErrorResponse {
    private String message;
    private List<ErrorDetail> errors;

    public ErrorResponse(String message, List<ErrorDetail> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }
}

package com.team.sortapp.exeption;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

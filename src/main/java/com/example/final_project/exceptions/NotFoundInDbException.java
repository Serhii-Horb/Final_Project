package com.example.final_project.exceptions;

public class NotFoundInDbException extends RuntimeException {
    public NotFoundInDbException(String message) {
        super(message);
    }
}

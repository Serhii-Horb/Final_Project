package com.example.final_project.aspect;

public class LogException extends RuntimeException {
    public LogException() {
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Exception cause) {
        super(message, cause);
    }
}

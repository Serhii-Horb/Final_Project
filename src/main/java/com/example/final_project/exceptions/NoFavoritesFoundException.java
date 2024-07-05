package com.example.final_project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoFavoritesFoundException extends RuntimeException {
    public NoFavoritesFoundException(String message) {
        super(message);
    }
}

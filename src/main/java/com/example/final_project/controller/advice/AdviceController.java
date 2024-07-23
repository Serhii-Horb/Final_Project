package com.example.final_project.controller.advice;

import com.example.final_project.exceptions.AuthorizationException;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NoUsersFoundException;
import com.example.final_project.exceptions.NotFoundInDbException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AdviceController {
    /**
     * Handles exceptions of type {@link NotFoundInDbException}.
     * Returns a 404 Not Found status with an error message.
     *
     * @param exception the thrown {@link NotFoundInDbException}
     * @return a {@link ResponseEntity} with status 404 and an {@link ErrorMessage}
     */
    @ExceptionHandler(NotFoundInDbException.class)
    public ResponseEntity<ErrorMessage> handleException(NotFoundInDbException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    /**
     * Handles exceptions of type {@link BadRequestException}.
     * Returns a 400 Bad Request status with an error message.
     *
     * @param exception the thrown {@link BadRequestException}
     * @return a {@link ResponseEntity} with status 400 and an {@link ErrorMessage}
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleException(BadRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    /**
     * Handles exceptions of type {@link AuthorizationException}.
     * Returns a 401 Unauthorized status with an error message.
     *
     * @param exception the thrown {@link AuthorizationException}
     * @return a {@link ResponseEntity} with status 401 and an {@link ErrorMessage}
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorMessage> handleException(AuthorizationException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(exception.getMessage()));
    }

    /**
     * Handles exceptions of type {@link NoUsersFoundException}.
     * Returns a 400 Bad Request status with an error message.
     *
     * @param exception the thrown {@link NoUsersFoundException}
     * @return a {@link ResponseEntity} with status 400 and an {@link ErrorMessage}
     */
    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(NoUsersFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}

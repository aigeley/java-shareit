package ru.practicum.shareit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Order
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleThrowable(Throwable e) {
        return sendError(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<String> sendError(Throwable e, HttpStatus httpStatus) {
        log.info("{} {}: {}", httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), httpStatus);
    }
}

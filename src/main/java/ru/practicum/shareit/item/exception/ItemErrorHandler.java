package ru.practicum.shareit.item.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ru.practicum.shareit.ErrorHandler.sendError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ItemErrorHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleItemOwnerIsDifferentException(ItemOwnerIsDifferentException e) {
        return sendError(e, HttpStatus.FORBIDDEN);
    }
}

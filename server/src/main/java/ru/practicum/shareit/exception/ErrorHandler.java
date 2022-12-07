package ru.practicum.shareit.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order
@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ErrorHandlerAbs {
    public ErrorHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleThrowable(Throwable e) throws JsonProcessingException {
        return sendError(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

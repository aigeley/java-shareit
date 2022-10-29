package ru.practicum.shareit.booking.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.ErrorHandlerAbs;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class BookingErrorHandler extends ErrorHandlerAbs {
    public BookingErrorHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBookingUserIsDifferentException(BookingUserIsDifferentException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBookingItemIsUnavailableException(BookingItemIsUnavailableException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBookingIsAlreadyApprovedException(BookingIsAlreadyApprovedException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBookingBookerSameAsOwnerException(BookingBookerSameAsOwnerException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.NOT_FOUND);
    }
}

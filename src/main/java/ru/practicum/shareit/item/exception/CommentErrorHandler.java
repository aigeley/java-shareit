package ru.practicum.shareit.item.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.ErrorHandlerAbs;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CommentErrorHandler extends ErrorHandlerAbs {
    @ExceptionHandler
    public ResponseEntity<String> handleCommentAuthorHasNotBookedItemException(CommentAuthorHasNotBookedItemException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }
}

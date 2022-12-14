package ru.practicum.shareit.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@AllArgsConstructor
public abstract class ErrorHandlerAbs {
    protected final ObjectMapper objectMapper;

    private String getJsonMessage(String customMessage) throws JsonProcessingException {
        return objectMapper.writeValueAsString(new ErrorResponse(customMessage));
    }

    public ResponseEntity<String> sendError(Throwable e, HttpStatus httpStatus) throws JsonProcessingException {
        return sendErrorCustomMessage(e, null, httpStatus);
    }

    public ResponseEntity<String> sendErrorCustomMessage(Throwable e, String customMessage, HttpStatus httpStatus)
            throws JsonProcessingException {
        String message = (customMessage == null || customMessage.isBlank()) ? e.getMessage() : customMessage;
        log.info("{} {}: {}", httpStatus.value(), httpStatus.getReasonPhrase(), message, e);
        return new ResponseEntity<>(getJsonMessage(message), httpStatus);
    }
}

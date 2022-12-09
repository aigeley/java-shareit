package ru.practicum.shareit.exception;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class ErrorResponse {
    String error;
}

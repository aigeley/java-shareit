package ru.practicum.shareit.user.exception;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException(String elementName, String email) {
        super(String.format("%s с email = %s уже существует", elementName, email));
    }
}

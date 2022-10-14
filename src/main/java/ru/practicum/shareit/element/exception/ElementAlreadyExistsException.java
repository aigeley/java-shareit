package ru.practicum.shareit.element.exception;

public class ElementAlreadyExistsException extends RuntimeException {
    public ElementAlreadyExistsException(String elementName, long id) {
        super(String.format("%s с id = %d уже существует", elementName, id));
    }
}

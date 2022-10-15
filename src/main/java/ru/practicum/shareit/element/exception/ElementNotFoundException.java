package ru.practicum.shareit.element.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String elementName, long id) {
        super(String.format("%s с id = %d не существует", elementName, id));
    }
}

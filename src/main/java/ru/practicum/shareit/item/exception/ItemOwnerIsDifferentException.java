package ru.practicum.shareit.item.exception;

public class ItemOwnerIsDifferentException extends RuntimeException {
    public ItemOwnerIsDifferentException(String elementName, long itemId, long userId) {
        super(String.format("%s с id = %d не пренадлежит пользователю с id = %d", elementName, itemId, userId));
    }
}

package ru.practicum.shareit.item.exception;

public class CommentAuthorHasNotBookedItemException extends RuntimeException {
    public CommentAuthorHasNotBookedItemException(long userId, long itemId) {
        super(String.format("пользователь с id = %d не может оставить отзыв на вещь с id = %d, которую не арендовал",
                userId, itemId));
    }
}

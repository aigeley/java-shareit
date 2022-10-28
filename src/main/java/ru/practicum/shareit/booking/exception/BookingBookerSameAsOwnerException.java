package ru.practicum.shareit.booking.exception;

public class BookingBookerSameAsOwnerException extends RuntimeException {
    public BookingBookerSameAsOwnerException(long userId, long itemId) {
        super(String.format("пользователь с id = %d не может забронировать свою вещь с id = %d", userId, itemId));
    }
}

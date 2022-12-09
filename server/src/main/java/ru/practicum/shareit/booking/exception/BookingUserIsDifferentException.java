package ru.practicum.shareit.booking.exception;

public class BookingUserIsDifferentException extends RuntimeException {
    public BookingUserIsDifferentException(String elementName, long bookingId, long userId) {
        super(String.format("%s с id = %d не доступно пользователю с id = %d", elementName, bookingId, userId));
    }
}

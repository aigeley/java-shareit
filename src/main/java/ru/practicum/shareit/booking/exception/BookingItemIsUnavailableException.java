package ru.practicum.shareit.booking.exception;

public class BookingItemIsUnavailableException extends RuntimeException {
    public BookingItemIsUnavailableException(long itemId) {
        super(String.format("вещь с id = %d не доступна для бронирования", itemId));
    }
}

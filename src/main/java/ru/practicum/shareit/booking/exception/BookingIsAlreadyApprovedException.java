package ru.practicum.shareit.booking.exception;

public class BookingIsAlreadyApprovedException extends RuntimeException {
    public BookingIsAlreadyApprovedException(String elementName, long bookingId) {
        super(String.format("%s с id = %d не может быть подтверждено повторно", elementName, bookingId));
    }
}

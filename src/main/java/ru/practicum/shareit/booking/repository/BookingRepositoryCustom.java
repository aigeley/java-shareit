package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

public interface BookingRepositoryCustom {
    List<Booking> getAllByBooker(BookingState state, long userId, Pageable pageable);

    List<Booking> getAllByOwner(BookingState state, long userId, Pageable pageable);

    Booking getLastBooking(long itemId);

    Booking getNextBooking(long itemId);

    boolean hasUserBookedItemInPast(long userId, long itemId);
}

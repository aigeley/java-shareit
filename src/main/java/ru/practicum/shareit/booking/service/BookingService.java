package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.element.service.ElementService;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface BookingService extends ElementService<Booking> {
    void checkUserIsBookerOrOwner(Booking booking, long userId);

    void checkItemIsAvailable(Item item);

    void checkBookingIsAlreadyApproved(Booking booking);

    void checkBookerIsNotOwner(long ownerId, long bookerId, long itemId);

    BookingWithEntitiesDto get(Long bookingId, Long userId);

    List<BookingWithEntitiesDto> getAllByBooker(Integer from, Integer size, BookingState state, Long userId);

    List<BookingWithEntitiesDto> getAllByOwner(Integer from, Integer size, BookingState state, Long userId);

    BookingWithEntitiesDto add(Long userId, BookingDto bookingDto);

    BookingWithEntitiesDto approve(Long bookingId, Boolean approved, Long userId);
}

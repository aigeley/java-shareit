package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingDtoWithEntities;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.element.service.ElementService;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface BookingService extends ElementService<Booking> {
    void checkUserIsBookerOrOwner(Booking booking, long userId);

    void checkItemIsAvailable(Item item);

    void checkBookingIsAlreadyApproved(Booking booking);

    void checkBookerIsNotOwner(long ownerId, long bookerId, long itemId);

    BookingDtoWithEntities get(long bookingId, long userId);

    List<BookingDtoWithEntities> getAllByBooker(BookingState state, long userId);

    List<BookingDtoWithEntities> getAllByOwner(BookingState state, long userId);

    BookingDtoWithEntities add(long userId, BookingDto bookingDto);

    BookingDtoWithEntities approve(long bookingId, Boolean approved, long userId);
}

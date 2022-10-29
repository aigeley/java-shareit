package ru.practicum.shareit.booking.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.model.ItemMapper.toItemDto;
import static ru.practicum.shareit.user.model.UserMapper.toUserDto;

public class BookingMapper {
    private BookingMapper() {
    }

    public static BookingDto toBookingDto(Booking booking) {
        return booking == null ? null : new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }

    public static BookingDtoWithEntities toBookingOutDto(Booking booking) {
        return booking == null ? null : new BookingDtoWithEntities(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                toItemDto(booking.getItem()),
                toUserDto(booking.getBooker()),
                booking.getStatus()
        );
    }

    public static List<BookingDtoWithEntities> toBookingOutDtoList(List<Booking> bookingsList) {
        return bookingsList == null ? Collections.emptyList() : bookingsList.stream()
                .map(BookingMapper::toBookingOutDto)
                .collect(Collectors.toList());
    }

    public static Booking toBooking(Booking booking, BookingDto bookingDto) {
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }
}

package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;

@Component
public class BookingDtoMapper extends ElementDtoMapperAbs<Booking, BookingDto> {
    public BookingDtoMapper() {
        super(
                BookingDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public BookingDto toDto(Booking booking) {
        return booking == null ? null : new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }

    @Override
    public Booking toElement(Booking booking, BookingDto bookingDto) {
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }
}

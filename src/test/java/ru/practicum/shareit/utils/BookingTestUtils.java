package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;

@Component
public class BookingTestUtils extends ElementTestUtils<Booking, BookingDto> {
    public BookingTestUtils(ElementDtoMapperAbs<Booking, BookingDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

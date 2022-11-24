package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;

@Component
public class BookingWithEntitiesTestUtils extends ElementTestUtils<Booking, BookingWithEntitiesDto> {
    public BookingWithEntitiesTestUtils(ElementDtoMapperAbs<Booking, BookingWithEntitiesDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

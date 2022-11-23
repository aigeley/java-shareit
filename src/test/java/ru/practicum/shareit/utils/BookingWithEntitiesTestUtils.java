package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.element.model.ElementDtoMapper;

@Component
public class BookingWithEntitiesTestUtils extends ElementTestUtils<Booking, BookingWithEntitiesDto> {
    public BookingWithEntitiesTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<Booking, BookingWithEntitiesDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

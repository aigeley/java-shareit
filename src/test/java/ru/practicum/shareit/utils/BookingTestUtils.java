package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.element.model.ElementDtoMapper;

@Component
public class BookingTestUtils extends ElementTestUtils<Booking, BookingDto> {
    public BookingTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<Booking, BookingDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

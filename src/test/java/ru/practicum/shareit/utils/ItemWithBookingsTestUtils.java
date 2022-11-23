package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.element.model.ElementDtoMapper;
import ru.practicum.shareit.item.model.ItemWithBookings;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;

@Component
public class ItemWithBookingsTestUtils extends ElementTestUtils<ItemWithBookings, ItemWithBookingsDto> {
    public ItemWithBookingsTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<ItemWithBookings, ItemWithBookingsDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

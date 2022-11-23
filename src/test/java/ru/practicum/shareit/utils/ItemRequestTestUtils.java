package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.element.model.ElementDtoMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;

@Component
public class ItemRequestTestUtils extends ElementTestUtils<ItemRequest, ItemRequestDto> {
    public ItemRequestTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<ItemRequest, ItemRequestDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

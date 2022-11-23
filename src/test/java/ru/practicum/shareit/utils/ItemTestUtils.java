package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.element.model.ElementDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;

@Component
public class ItemTestUtils extends ElementTestUtils<Item, ItemDto> {
    public ItemTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<Item, ItemDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

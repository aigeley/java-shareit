package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.element.model.ElementDtoMapper;
import ru.practicum.shareit.request.model.ItemRequestWithAnswers;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;

@Component
public class ItemRequestWithAnswersTestUtils extends ElementTestUtils<ItemRequestWithAnswers, ItemRequestWithAnswersDto> {
    public ItemRequestWithAnswersTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<ItemRequestWithAnswers, ItemRequestWithAnswersDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

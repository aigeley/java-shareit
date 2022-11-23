package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.element.model.ElementDtoMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentDto;

@Component
public class CommentTestUtils extends ElementTestUtils<Comment, CommentDto> {
    public CommentTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<Comment, CommentDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

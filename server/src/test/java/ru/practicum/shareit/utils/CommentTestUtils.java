package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentDto;

@Component
public class CommentTestUtils extends ElementTestUtils<Comment, CommentDto> {
    public CommentTestUtils(ElementDtoMapperAbs<Comment, CommentDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

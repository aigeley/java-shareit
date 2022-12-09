package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Component
public class CommentDtoMapper extends ElementDtoMapperAbs<Comment, CommentDto> {
    public CommentDtoMapper() {
        super(
                CommentDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public CommentDto toDto(Comment comment) {
        return comment == null ? null : new CommentDto(
                comment.getId(),
                comment.getText(),
                Optional.ofNullable(comment.getItem()).map(Item::getId).orElse(null),
                Optional.ofNullable(comment.getAuthor()).map(User::getName).orElse(null),
                comment.getCreated()
        );
    }

    @Override
    public Comment toElement(Comment comment, CommentDto commentDto) {
        comment.setText(commentDto.getText());
        return comment;
    }
}

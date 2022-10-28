package ru.practicum.shareit.item.model;

import ru.practicum.shareit.user.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentMapper {
    private CommentMapper() {
    }

    public static CommentDto toCommentDto(Comment comment) {
        return comment == null ? null : new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItem().getId(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> commentsList) {
        return commentsList == null ? Collections.emptyList() : commentsList.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public static Comment toComment(Comment comment, CommentDto commentDto, Item item, User author) {
        Optional.ofNullable(commentDto.getText()).ifPresent(comment::setText);
        Optional.ofNullable(item).ifPresent(comment::setItem);
        Optional.ofNullable(author).ifPresent(comment::setAuthor);
        return comment;
    }
}

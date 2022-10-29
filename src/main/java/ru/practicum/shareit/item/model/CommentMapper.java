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
                Optional.ofNullable(comment.getItem()).map(Item::getId).orElse(null),
                Optional.ofNullable(comment.getAuthor()).map(User::getName).orElse(null),
                comment.getCreated()
        );
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> commentsList) {
        return commentsList == null ? Collections.emptyList() : commentsList.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public static Comment toComment(Comment comment, CommentDto commentDto) {
        comment.setText(commentDto.getText());
        return comment;
    }
}

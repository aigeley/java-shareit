package ru.practicum.shareit.item.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Element;

import java.time.LocalDateTime;

@Value
@ToString
public class CommentDto extends Element {
    /**
     * уникальный идентификатор комментария
     */
    Long id;
    /**
     * содержимое комментария
     */
    String text;
    /**
     * вещь, к которой относится комментарий
     */
    Long itemId;
    /**
     * автор комментария
     */
    String authorName;
    /**
     * дата создания комментария
     */
    LocalDateTime created;
}

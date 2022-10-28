package ru.practicum.shareit.item.model;

import lombok.Value;
import ru.practicum.shareit.element.model.Create;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Value
public class CommentDto {
    /**
     * уникальный идентификатор комментария
     */
    Long id;
    /**
     * содержимое комментария
     */
    @NotBlank(groups = {Create.class})
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

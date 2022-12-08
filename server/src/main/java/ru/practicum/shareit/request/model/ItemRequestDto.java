package ru.practicum.shareit.request.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Element;

import java.time.LocalDateTime;

@Value
@ToString
public class ItemRequestDto extends Element {
    /**
     * уникальный идентификатор запроса
     */
    Long id;
    /**
     * текст запроса, содержащий описание требуемой вещи
     */
    String description;
    /**
     * пользователь, создавший запрос
     */
    Long requestorId;
    /**
     * дата и время создания запроса
     */
    LocalDateTime created;
}

package ru.practicum.shareit.request.model;

import lombok.Value;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Value
public class ItemRequestDto {
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
    User requestor;
    /**
     * дата и время создания запроса
     */
    LocalDateTime created;
}

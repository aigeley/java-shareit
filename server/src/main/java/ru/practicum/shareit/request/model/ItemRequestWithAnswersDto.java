package ru.practicum.shareit.request.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Identifiable;
import ru.practicum.shareit.item.model.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Value
@ToString
public class ItemRequestWithAnswersDto implements Identifiable {
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
    /**
     * список ответов на запрос
     */
    List<ItemDto> items;
}

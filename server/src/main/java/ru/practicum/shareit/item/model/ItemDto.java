package ru.practicum.shareit.item.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Identifiable;

@Value
@ToString
public class ItemDto implements Identifiable {
    /**
     * уникальный идентификатор вещи
     */
    Long id;
    /**
     * краткое название
     */
    String name;
    /**
     * развёрнутое описание
     */
    String description;
    /**
     * статус о том, доступна или нет вещь для аренды
     */
    Boolean available;
    /**
     * владелец вещи
     */
    Long ownerId;
    /**
     * если вещь была создана по запросу другого пользователя,
     * то в этом поле будет храниться ссылка на соответствующий запрос
     */
    Long requestId;
}

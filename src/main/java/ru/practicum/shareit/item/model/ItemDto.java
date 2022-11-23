package ru.practicum.shareit.item.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Create;
import ru.practicum.shareit.element.model.Identifiable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank(groups = {Create.class})
    String name;
    /**
     * развёрнутое описание
     */
    @NotBlank(groups = {Create.class})
    String description;
    /**
     * статус о том, доступна или нет вещь для аренды
     */
    @NotNull(groups = {Create.class})
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

package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.practicum.shareit.element.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor
public class ItemDto {
    long id; //уникальный идентификатор вещи;
    @NotBlank(groups = {Create.class})
    String name; //краткое название;
    @NotBlank(groups = {Create.class})
    String description; //развёрнутое описание;
    @NotNull(groups = {Create.class})
    Boolean available; //статус о том, доступна или нет вещь для аренды;
    //если вещь была создана по запросу другого пользователя,
    //то в этом поле будет храниться ссылка на соответствующий запрос.
    long requestId;
}

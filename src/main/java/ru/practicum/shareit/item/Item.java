package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.practicum.shareit.element.Identifiable;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

@Value
@AllArgsConstructor
public class Item implements Identifiable {
    long id; //уникальный идентификатор вещи;
    String name; //краткое название;
    String description; //развёрнутое описание;
    Boolean available; //статус о том, доступна или нет вещь для аренды;
    User owner; //владелец вещи;
    //если вещь была создана по запросу другого пользователя,
    //то в этом поле будет храниться ссылка на соответствующий запрос.
    ItemRequest request;
}

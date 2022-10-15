package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.practicum.shareit.user.User;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor
public class ItemRequestDto {
    long id; //уникальный идентификатор запроса;
    String description; //текст запроса, содержащий описание требуемой вещи;
    User requestor; //пользователь, создавший запрос;
    ZonedDateTime created; //дата и время создания запроса.
}

package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.practicum.shareit.element.Identifiable;

@Value
@AllArgsConstructor
public class User implements Identifiable {
    long id; //уникальный идентификатор пользователя;
    String name; //имя или логин пользователя;
    String email; //адрес электронной почты
}

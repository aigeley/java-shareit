package ru.practicum.shareit.user.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Identifiable;

@Value
@ToString
public class UserDto implements Identifiable {
    /**
     * уникальный идентификатор пользователя
     */
    Long id;
    /**
     * имя или логин пользователя
     */
    String name;
    /**
     * адрес электронной почты
     */
    String email;
}

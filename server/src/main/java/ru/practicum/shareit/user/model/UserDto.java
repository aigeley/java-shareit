package ru.practicum.shareit.user.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Element;

@Value
@ToString
public class UserDto extends Element {
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

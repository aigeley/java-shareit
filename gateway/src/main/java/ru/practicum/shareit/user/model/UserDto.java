package ru.practicum.shareit.user.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Create;
import ru.practicum.shareit.element.model.Element;
import ru.practicum.shareit.element.model.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    @Email(groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    String email;
}

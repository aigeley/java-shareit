package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.practicum.shareit.element.Create;
import ru.practicum.shareit.element.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@AllArgsConstructor
public class UserDto {
    long id; //уникальный идентификатор пользователя;
    String name; //имя или логин пользователя;
    @Email(groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    String email; //адрес электронной почты
}

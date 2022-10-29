package ru.practicum.shareit.user.service;

import ru.practicum.shareit.element.service.ElementService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

public interface UserService extends ElementService<User> {
    UserDto get(long userId);

    List<UserDto> getAll();

    UserDto add(UserDto userDto);

    UserDto update(long userId, UserDto userDto);
}

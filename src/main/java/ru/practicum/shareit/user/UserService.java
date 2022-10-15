package ru.practicum.shareit.user;

import ru.practicum.shareit.element.ElementService;

import java.util.Collection;

public interface UserService extends ElementService<User> {
    UserDto get(long userId);

    Collection<UserDto> getAll();

    UserDto add(UserDto userDto);

    UserDto update(long userId, UserDto userDto);
}

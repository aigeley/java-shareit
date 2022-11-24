package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

@Component
public class UserTestUtils extends ElementTestUtils<User, UserDto> {
    public UserTestUtils(ElementDtoMapperAbs<User, UserDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

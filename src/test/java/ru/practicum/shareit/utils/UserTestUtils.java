package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.element.model.ElementDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

@Component
public class UserTestUtils extends ElementTestUtils<User, UserDto> {
    public UserTestUtils(MockMvc mockMvc, ObjectMapper objectMapper, ElementDtoMapper<User, UserDto> elementDtoMapper) {
        super(mockMvc, objectMapper, elementDtoMapper);
    }
}

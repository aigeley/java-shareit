package ru.practicum.shareit.user.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;

import java.util.Optional;

@Component
public class UserDtoMapper extends ElementDtoMapperAbs<User, UserDto> {
    public UserDtoMapper() {
        super(
                UserDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public UserDto toDto(User user) {
        return user == null ? null : new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @Override
    public User toElement(User user, UserDto userDto) {
        Optional.ofNullable(userDto.getName()).ifPresent(user::setName);
        Optional.ofNullable(userDto.getEmail()).ifPresent(user::setEmail);
        return user;
    }
}

package ru.practicum.shareit.user;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper {
    private UserMapper() {
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static Collection<UserDto> toUsersDtoList(Collection<User> usersList) {
        return usersList.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public static User toUser(UserDto userDto, long userId) {
        return new User(
                userId,
                userDto.getName(),
                userDto.getEmail()
        );
    }
}

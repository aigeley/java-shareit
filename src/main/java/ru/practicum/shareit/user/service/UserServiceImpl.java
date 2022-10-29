package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.service.ElementServiceAbs;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static ru.practicum.shareit.user.model.UserMapper.toUser;
import static ru.practicum.shareit.user.model.UserMapper.toUserDto;
import static ru.practicum.shareit.user.model.UserMapper.toUserDtoList;

@Slf4j
@Service
public class UserServiceImpl extends ElementServiceAbs<User> implements UserService {
    public static final String ELEMENT_NAME = "пользователь";
    protected final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(ELEMENT_NAME, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public UserDto get(long userId) {
        return toUserDto(getElement(userId));
    }

    @Override
    public List<UserDto> getAll() {
        return toUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto add(UserDto userDto) {
        User user = toUser(new User(), userDto);
        User userAdded = userRepository.save(user);
        log.info("add: " + userAdded);
        return toUserDto(userAdded);
    }

    @Override
    public UserDto update(long userId, UserDto userDto) {
        User user = toUser(getElement(userId), userDto);
        User userUpdated = userRepository.save(user);
        log.info("update: " + userUpdated);
        return toUserDto(userUpdated);
    }
}

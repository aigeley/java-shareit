package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.service.ElementServiceAbs;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserDtoMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ElementServiceAbs<User> implements UserService {
    public static final String ELEMENT_NAME = "пользователь";
    private final UserDtoMapper userDtoMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserDtoMapper userDtoMapper, UserRepository userRepository) {
        super(ELEMENT_NAME, userRepository);
        this.userDtoMapper = userDtoMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto get(Long userId) {
        return userDtoMapper.toDto(getAndCheckElement(userId));
    }

    @Override
    public List<UserDto> getAll() {
        return userDtoMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDto add(UserDto userDto) {
        User user = userDtoMapper.toElement(new User(), userDto);
        User userAdded = userRepository.save(user);
        log.info("add: " + userAdded);
        return userDtoMapper.toDto(userAdded);
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) {
        User user = userDtoMapper.toElement(getAndCheckElement(userId), userDto);
        User userUpdated = userRepository.save(user);
        log.info("update: " + userUpdated);
        return userDtoMapper.toDto(userUpdated);
    }
}

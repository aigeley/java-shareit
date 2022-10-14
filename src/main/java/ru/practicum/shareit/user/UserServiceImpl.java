package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.ElementServiceAbs;
import ru.practicum.shareit.user.exception.UserEmailAlreadyExistsException;

import java.util.Collection;

import static ru.practicum.shareit.user.UserMapper.toUser;
import static ru.practicum.shareit.user.UserMapper.toUserDto;
import static ru.practicum.shareit.user.UserMapper.toUsersDtoList;

@Slf4j
@Service
public class UserServiceImpl extends ElementServiceAbs<User> implements UserService {
    public static final String ELEMENT_NAME = "пользователь";
    protected final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(ELEMENT_NAME, userRepository);
        this.userRepository = userRepository;
    }

    private void checkIfUserEmailAlreadyExists(String email) {
        if (userRepository.isEmailExists(email)) {
            throw new UserEmailAlreadyExistsException(elementName, email);
        }
    }

    @Override
    public UserDto get(long userId) {
        return toUserDto(getElement(userId));
    }

    @Override
    public Collection<UserDto> getAll() {
        return toUsersDtoList(userRepository.getAll());
    }

    @Override
    public UserDto add(UserDto userDto) {
        checkIfUserEmailAlreadyExists(userDto.getEmail());
        User user = toUser(userDto, userRepository.getNextId());
        log.info("add: " + user);
        return toUserDto(userRepository.add(user));
    }

    @Override
    public UserDto update(long userId, UserDto userDto) {
        User oldUser = getElement(userId);
        String name = userDto.getName();
        String email = userDto.getEmail();

        if (email != null) {
            checkIfUserEmailAlreadyExists(email);
        }

        User user = new User(
                userId,
                name != null ? name : oldUser.getName(),
                email != null ? email : oldUser.getEmail()
        );

        log.info("update: " + user);
        return toUserDto(userRepository.update(user));
    }

    @Override
    public void delete(long userId) {
        deleteElement(userId);
    }
}

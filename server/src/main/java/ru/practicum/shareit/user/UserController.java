package ru.practicum.shareit.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.element.ElementControllerAbs;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static ru.practicum.shareit.user.UserController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class UserController extends ElementControllerAbs<User> {
    public static final String BASE_PATH = "/users";
    private final UserService userService;

    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public UserDto get(
            @PathVariable("id") Long userId
    ) {
        return userService.get(userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public UserDto add(
            @RequestBody UserDto userDto
    ) {
        return userService.add(userDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public UserDto update(
            @PathVariable("id") Long userId,
            @RequestBody UserDto userDto
    ) {
        return userService.update(userId, userDto);
    }
}

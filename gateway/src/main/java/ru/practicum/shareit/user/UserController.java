package ru.practicum.shareit.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.element.ElementControllerAbs;
import ru.practicum.shareit.element.model.Create;
import ru.practicum.shareit.element.model.Update;
import ru.practicum.shareit.user.client.UserClient;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

import static ru.practicum.shareit.user.UserController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class UserController extends ElementControllerAbs {
    public static final String BASE_PATH = "/users";
    private final UserClient userClient;

    public UserController(UserClient userClient) {
        super(userClient);
        this.userClient = userClient;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public UserDto get(
            @PathVariable("id") Long userId
    ) {
        return userClient.get(userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<UserDto> getAll() {
        return userClient.getAll();
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public UserDto add(
            @Validated({Create.class}) @RequestBody UserDto userDto
    ) {
        return userClient.add(userDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public UserDto update(
            @PathVariable("id") Long userId,
            @Validated({Update.class}) @RequestBody UserDto userDto
    ) {
        return userClient.update(userId, userDto);
    }
}

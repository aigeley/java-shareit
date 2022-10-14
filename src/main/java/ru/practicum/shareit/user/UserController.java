package ru.practicum.shareit.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.element.Create;
import ru.practicum.shareit.element.Update;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public UserDto get(@PathVariable("id") long userId) {
        return userService.get(userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public Collection<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public UserDto add(@Validated({Create.class}) @RequestBody UserDto userDto) {
        return userService.add(userDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public UserDto update(@PathVariable("id") long userId, @Validated({Update.class}) @RequestBody UserDto userDto) {
        return userService.update(userId, userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long userId) {
        userService.delete(userId);
    }

    @DeleteMapping
    public void deleteAll() {
        userService.deleteAll();
    }
}

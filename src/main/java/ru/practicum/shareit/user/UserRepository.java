package ru.practicum.shareit.user;

import ru.practicum.shareit.element.ElementRepository;

import java.util.Collection;

public interface UserRepository extends ElementRepository<User> {
    boolean isEmailExists(String email);

    Collection<User> getAll();
}

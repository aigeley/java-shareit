package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.ElementRepositoryInMemoryAbs;

import java.util.Collection;

@Component
public class UserRepositoryInMemoryImpl extends ElementRepositoryInMemoryAbs<User> implements UserRepository {
    @Override
    public boolean isEmailExists(String email) {
        return elements
                .values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public Collection<User> getAll() {
        return elements.values();
    }
}

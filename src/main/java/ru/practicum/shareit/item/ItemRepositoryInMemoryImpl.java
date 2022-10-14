package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.ElementRepositoryInMemoryAbs;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryInMemoryImpl extends ElementRepositoryInMemoryAbs<Item> implements ItemRepository {
    @Override
    public Collection<Item> search(String text) {
        return elements.values().stream()
                .filter(
                        item -> (item.getName().toLowerCase().contains(text.toLowerCase())
                                || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                                && item.getAvailable()
                                && !text.isBlank()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Item> getAll(long userId) {
        return elements.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }
}

package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.ElementRepositoryInMemoryAbs;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class ItemRepositoryInMemoryImpl extends ElementRepositoryInMemoryAbs<Item> implements ItemRepository {
    @Override
    public Collection<Item> search(String text) {
        return elements.values().stream()
                .filter(
                        item -> (containsIgnoreCase(item.getName(), text)
                                || containsIgnoreCase(item.getDescription(), text))
                                && item.getAvailable()
                                && isNotBlank(text)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Item> getAll(long userId) {
        return elements.values().stream()
                .filter(item -> (item.getOwner() != null && item.getOwner().getId() == userId))
                .collect(Collectors.toList());
    }
}

package ru.practicum.shareit.item;

import ru.practicum.shareit.element.ElementRepository;

import java.util.Collection;

public interface ItemRepository extends ElementRepository<Item> {
    Collection<Item> search(String text);

    Collection<Item> getAll(long userId);
}

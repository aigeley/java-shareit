package ru.practicum.shareit.item;

import ru.practicum.shareit.element.ElementService;

import java.util.Collection;

public interface ItemService extends ElementService<Item> {
    ItemDto get(long itemId);

    Collection<ItemDto> getAll(long userId);

    Collection<ItemDto> search(String text);

    ItemDto add(long itemId, ItemDto item);

    ItemDto update(long itemId, long userId, ItemDto item);

    void delete(long itemId);
}

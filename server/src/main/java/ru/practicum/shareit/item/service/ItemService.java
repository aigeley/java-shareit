package ru.practicum.shareit.item.service;

import ru.practicum.shareit.element.service.ElementService;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;

import java.util.List;

public interface ItemService extends ElementService<Item> {
    void checkItemBelongsToUser(Item item, long userId);

    void checkUserHasBookedItemInPast(long userId, long itemId);

    ItemWithBookingsDto get(Long itemId, Long userId);

    List<ItemWithBookingsDto> getAll(Integer from, Integer size, Long userId);

    List<ItemDto> search(Integer from, Integer size, String text, Long userId);

    ItemDto add(Long itemId, ItemDto item);

    CommentDto addComment(Long itemId, Long userId, CommentDto commentDto);

    ItemDto update(Long itemId, Long userId, ItemDto item);
}

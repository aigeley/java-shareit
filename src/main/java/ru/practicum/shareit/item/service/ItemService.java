package ru.practicum.shareit.item.service;

import ru.practicum.shareit.element.service.ElementService;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemDtoWithBookings;

import java.util.List;

public interface ItemService extends ElementService<Item> {
    void checkItemBelongsToUser(Item item, long userId);

    void checkUserHasBookedItemInPast(long userId, long itemId);

    ItemDtoWithBookings get(long itemId, long userId);

    List<ItemDtoWithBookings> getAll(long userId);

    List<ItemDto> search(String text);

    ItemDto add(long itemId, ItemDto item);

    CommentDto addComment(long itemId, long userId, CommentDto commentDto);

    ItemDto update(long itemId, long userId, ItemDto item);
}

package ru.practicum.shareit.item;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.element.ElementControllerAbs;
import ru.practicum.shareit.element.model.Create;
import ru.practicum.shareit.element.model.Update;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;

import java.util.List;

import static ru.practicum.shareit.item.ItemController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class ItemController extends ElementControllerAbs {
    public static final String BASE_PATH = "/items";
    private final ItemClient itemClient;

    public ItemController(ItemClient itemClient) {
        super(itemClient);
        this.itemClient = itemClient;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemWithBookingsDto get(
            @PathVariable("id") Long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemClient.get(itemId, userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ItemWithBookingsDto> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemClient.getAll(from, size, userId);
    }

    @GetMapping(path = "/search", produces = "application/json;charset=UTF-8")
    public List<ItemDto> search(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam String text,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemClient.search(from, size, text, userId);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ItemDto add(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Validated({Create.class}) @RequestBody ItemDto itemDto
    ) {
        return itemClient.add(userId, itemDto);
    }

    @PostMapping(path = "/{id}/comment", produces = "application/json;charset=UTF-8")
    public CommentDto addComment(
            @PathVariable("id") Long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Validated({Create.class}) @RequestBody CommentDto commentDto
    ) {
        return itemClient.addComment(itemId, userId, commentDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemDto update(
            @PathVariable("id") Long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Validated({Update.class}) @RequestBody ItemDto itemDto
    ) {
        return itemClient.update(itemId, userId, itemDto);
    }
}

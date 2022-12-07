package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

import static ru.practicum.shareit.item.ItemController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class ItemController extends ElementControllerAbs<Item> {
    public static final String BASE_PATH = "/items";
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        super(itemService);
        this.itemService = itemService;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemWithBookingsDto get(
            @PathVariable("id") Long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemService.get(itemId, userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ItemWithBookingsDto> getAll(
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemService.getAll(from, size, userId);
    }

    @GetMapping(path = "/search", produces = "application/json;charset=UTF-8")
    public List<ItemDto> search(
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size,
            @RequestParam String text,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemService.search(from, size, text, userId);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ItemDto add(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemDto itemDto
    ) {
        return itemService.add(userId, itemDto);
    }

    @PostMapping(path = "/{id}/comment", produces = "application/json;charset=UTF-8")
    public CommentDto addComment(
            @PathVariable("id") Long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody CommentDto commentDto
    ) {
        return itemService.addComment(itemId, userId, commentDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemDto update(
            @PathVariable("id") Long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemDto itemDto
    ) {
        return itemService.update(itemId, userId, itemDto);
    }
}

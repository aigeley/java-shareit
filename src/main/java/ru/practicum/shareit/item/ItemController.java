package ru.practicum.shareit.item;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.element.Create;
import ru.practicum.shareit.element.Update;

import java.util.Collection;

@RestController
@RequestMapping("/items")
public class ItemController {
    ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemDto get(@PathVariable("id") long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.get(itemId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public Collection<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getAll(userId);
    }

    @GetMapping(path = "/search", produces = "application/json;charset=UTF-8")
    public Collection<ItemDto> search(@RequestParam String text, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.search(text);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") long userId,
                       @Validated({Create.class}) @RequestBody ItemDto itemDto) {
        return itemService.add(userId, itemDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemDto update(@PathVariable("id") long itemId, @RequestHeader("X-Sharer-User-Id") long userId,
                          @Validated({Update.class}) @RequestBody ItemDto itemDto) {
        return itemService.update(itemId, userId, itemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        itemService.delete(itemId);
    }

    @DeleteMapping
    public void deleteAll() {
        itemService.deleteAll();
    }
}

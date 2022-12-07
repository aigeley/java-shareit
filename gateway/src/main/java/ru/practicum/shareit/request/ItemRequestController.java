package ru.practicum.shareit.request;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.element.ElementControllerAbs;
import ru.practicum.shareit.element.model.Create;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;

import java.util.List;

import static ru.practicum.shareit.request.ItemRequestController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class ItemRequestController extends ElementControllerAbs {
    public static final String BASE_PATH = "/requests";
    private final ItemRequestClient itemRequestClient;

    public ItemRequestController(ItemRequestClient itemRequestClient) {
        super(itemRequestClient);
        this.itemRequestClient = itemRequestClient;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemRequestWithAnswersDto get(
            @PathVariable("id") Long itemRequestId,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemRequestClient.get(itemRequestId, userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ItemRequestWithAnswersDto> getAllByCurrentUser(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemRequestClient.getAllByCurrentUser(userId);
    }

    @GetMapping(path = "/all", produces = "application/json;charset=UTF-8")
    public List<ItemRequestWithAnswersDto> getAllByOtherUsers(
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemRequestClient.getAllByOtherUsers(from, size, userId);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ItemRequestDto add(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Validated({Create.class}) @RequestBody ItemRequestDto itemRequestDto
    ) {
        return itemRequestClient.add(userId, itemRequestDto);
    }
}

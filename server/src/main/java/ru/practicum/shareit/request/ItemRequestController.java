package ru.practicum.shareit.request;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.element.ElementControllerAbs;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

import static ru.practicum.shareit.request.ItemRequestController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class ItemRequestController extends ElementControllerAbs<ItemRequest> {
    public static final String BASE_PATH = "/requests";
    private final ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        super(itemRequestService);
        this.itemRequestService = itemRequestService;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ItemRequestWithAnswersDto get(
            @PathVariable("id") Long itemRequestId,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemRequestService.get(itemRequestId, userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ItemRequestWithAnswersDto> getAllByCurrentUser(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemRequestService.getAllByCurrentUser(userId);
    }

    @GetMapping(path = "/all", produces = "application/json;charset=UTF-8")
    public List<ItemRequestWithAnswersDto> getAllByOtherUsers(
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemRequestService.getAllByOtherUsers(from, size, userId);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ItemRequestDto add(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemRequestDto itemRequestDto
    ) {
        return itemRequestService.add(userId, itemRequestDto);
    }
}

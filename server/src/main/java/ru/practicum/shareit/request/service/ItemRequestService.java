package ru.practicum.shareit.request.service;

import ru.practicum.shareit.element.service.ElementService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;

import java.util.List;

public interface ItemRequestService extends ElementService<ItemRequest> {
    ItemRequestWithAnswersDto get(Long itemRequestId, Long userId);

    List<ItemRequestWithAnswersDto> getAllByCurrentUser(Long userId);

    List<ItemRequestWithAnswersDto> getAllByOtherUsers(Integer from, Integer size, Long userId);

    ItemRequestDto add(Long itemRequestId, ItemRequestDto itemRequest);
}

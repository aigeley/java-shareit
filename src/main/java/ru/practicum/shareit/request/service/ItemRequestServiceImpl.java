package ru.practicum.shareit.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.model.PageRequestFromElement;
import ru.practicum.shareit.element.service.ElementServiceAbs;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestDtoMapper;
import ru.practicum.shareit.request.model.ItemRequestWithAnswers;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDtoMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemRequestServiceImpl extends ElementServiceAbs<ItemRequest> implements ItemRequestService {
    public static final String ELEMENT_NAME = "запрос";
    private final ItemRequestRepository itemRequestRepository;
    @Autowired
    private ItemRequestDtoMapper itemRequestDtoMapper;
    @Autowired
    private ItemRequestWithAnswersDtoMapper itemRequestWithAnswersDtoMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemRepository itemRepository;

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository) {
        super(ELEMENT_NAME, itemRequestRepository);
        this.itemRequestRepository = itemRequestRepository;
    }

    @Override
    public ItemRequestWithAnswersDto get(Long itemRequestId, Long userId) {
        userService.getAndCheckElement(userId);
        ItemRequest itemRequest = getAndCheckElement(itemRequestId);
        return itemRequestWithAnswersDtoMapper.toDto(
                new ItemRequestWithAnswers(
                        itemRequest,
                        itemRepository.findByRequest_IdOrderById(itemRequest.getId())
                )
        );
    }

    @Override
    public List<ItemRequestWithAnswersDto> getAllByCurrentUser(Long userId) {
        userService.getAndCheckElement(userId);
        return itemRequestRepository.findByRequestor_IdOrderByIdDesc(userId)
                .stream()
                .map(itemRequest -> itemRequestWithAnswersDtoMapper.toDto(
                                new ItemRequestWithAnswers(
                                        itemRequest,
                                        itemRepository.findByRequest_IdOrderById(itemRequest.getId())
                                )
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestWithAnswersDto> getAllByOtherUsers(Integer from, Integer size, Long userId) {
        userService.getAndCheckElement(userId);
        return (from == null || size == null) ? Collections.emptyList() : itemRequestRepository
                .findByRequestor_IdNot(
                        userId,
                        PageRequestFromElement.ofSortByIdDesc(from, size)
                )
                .stream()
                .map(itemRequest -> itemRequestWithAnswersDtoMapper.toDto(
                                new ItemRequestWithAnswers(
                                        itemRequest,
                                        itemRepository.findByRequest_IdOrderById(itemRequest.getId())
                                )
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto add(Long userId, ItemRequestDto itemRequestDto) {
        User requestor = userService.getAndCheckElement(userId);

        ItemRequest itemRequest = itemRequestDtoMapper.toElement(new ItemRequest(), itemRequestDto);
        itemRequest.setRequestor(requestor);

        ItemRequest itemRequestAdded = itemRequestRepository.save(itemRequest);
        log.info("add: " + itemRequestAdded);
        return itemRequestDtoMapper.toDto(itemRequestAdded);
    }
}
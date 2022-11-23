package ru.practicum.shareit.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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
    private final ItemRequestDtoMapper itemRequestDtoMapper;
    private final ItemRequestWithAnswersDtoMapper itemRequestWithAnswersDtoMapper;
    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;

    public ItemRequestServiceImpl(ItemRequestDtoMapper itemRequestDtoMapper,
                                  ItemRequestWithAnswersDtoMapper itemRequestWithAnswersDtoMapper,
                                  ItemRequestRepository itemRequestRepository,
                                  UserService userService,
                                  ItemRepository itemRepository) {
        super(ELEMENT_NAME, itemRequestRepository);
        this.itemRequestDtoMapper = itemRequestDtoMapper;
        this.itemRequestWithAnswersDtoMapper = itemRequestWithAnswersDtoMapper;
        this.itemRequestRepository = itemRequestRepository;
        this.userService = userService;
        this.itemRepository = itemRepository;
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
                        PageRequestFromElement.of(from, size, Sort.by(Order.desc("id")))
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
package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.element.model.PageRequestFromElement;
import ru.practicum.shareit.element.service.ElementServiceAbs;
import ru.practicum.shareit.item.exception.CommentAuthorHasNotBookedItemException;
import ru.practicum.shareit.item.exception.ItemOwnerIsDifferentException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CommentDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemDtoMapper;
import ru.practicum.shareit.item.model.ItemWithBookings;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;
import ru.practicum.shareit.item.model.ItemWithBookingsDtoMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemServiceImpl extends ElementServiceAbs<Item> implements ItemService {
    public static final String ELEMENT_NAME = "вещь";
    private final ItemRepository itemRepository;
    @Autowired
    private ItemDtoMapper itemDtoMapper;
    @Autowired
    private ItemWithBookingsDtoMapper itemWithBookingsDtoMapper;
    @Autowired
    private CommentDtoMapper commentDtoMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRequestService itemRequestService;

    public ItemServiceImpl(ItemRepository itemRepository) {
        super(ELEMENT_NAME, itemRepository);
        this.itemRepository = itemRepository;
    }

    @Override
    public void checkItemBelongsToUser(Item item, long userId) {
        if (item.getOwner().getId() != userId) {
            throw new ItemOwnerIsDifferentException(elementName, item.getId(), userId);
        }
    }

    @Override
    public void checkUserHasBookedItemInPast(long userId, long itemId) {
        if (!bookingRepository.hasUserBookedItemInPast(userId, itemId)) {
            throw new CommentAuthorHasNotBookedItemException(userId, itemId);
        }
    }

    @Override
    public ItemWithBookingsDto get(Long itemId, Long userId) {
        userService.getAndCheckElement(userId);
        Item item = getAndCheckElement(itemId);
        long ownerId = item.getOwner().getId();
        return itemWithBookingsDtoMapper.toDto(
                new ItemWithBookings(
                        item,
                        userId != ownerId ? null : bookingRepository.getLastBooking(itemId),
                        userId != ownerId ? null : bookingRepository.getNextBooking(itemId),
                        commentRepository.findByItem_IdOrderById(itemId)
                )
        );
    }

    @Override
    public List<ItemWithBookingsDto> getAll(Integer from, Integer size, Long userId) {
        userService.getAndCheckElement(userId);
        return itemRepository.findByOwner_Id(
                        userId,
                        PageRequestFromElement.ofSortByIdAsc(from, size)
                )
                .stream()
                .map(item -> itemWithBookingsDtoMapper.toDto(
                                new ItemWithBookings(
                                        item,
                                        bookingRepository.getLastBooking(item.getId()),
                                        bookingRepository.getNextBooking(item.getId()),
                                        commentRepository.findByItem_IdOrderById(item.getId())
                                )
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(Integer from, Integer size, String text) {
        return itemDtoMapper.toDtoList(
                itemRepository.search(
                        text,
                        PageRequestFromElement.ofSortByIdAsc(from, size)
                )
        );
    }

    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        User owner = userService.getAndCheckElement(userId);
        ItemRequest itemRequest = Optional.ofNullable(itemDto.getRequestId())
                .map(itemRequestService::getAndCheckElement)
                .orElse(null);

        Item item = itemDtoMapper.toElement(new Item(), itemDto);
        item.setOwner(owner);
        item.setRequest(itemRequest);

        Item itemAdded = itemRepository.save(item);
        log.info("add: " + itemAdded);
        return itemDtoMapper.toDto(itemAdded);
    }

    @Override
    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto) {
        checkUserHasBookedItemInPast(userId, itemId);
        Item item = getAndCheckElement(itemId);
        User author = userService.getAndCheckElement(userId);

        Comment comment = commentDtoMapper.toElement(new Comment(), commentDto);
        comment.setItem(item);
        comment.setAuthor(author);

        Comment commentAdded = commentRepository.save(comment);
        log.info("addComment: " + commentAdded);
        return commentDtoMapper.toDto(commentAdded);
    }

    @Override
    public ItemDto update(Long itemId, Long userId, ItemDto itemDto) {
        userService.getAndCheckElement(userId);
        Item item = itemDtoMapper.toElement(getAndCheckElement(itemId), itemDto);
        checkItemBelongsToUser(item, userId);
        Item itemUpdated = itemRepository.save(item);
        log.info("update: " + itemUpdated);
        return itemDtoMapper.toDto(itemUpdated);
    }
}
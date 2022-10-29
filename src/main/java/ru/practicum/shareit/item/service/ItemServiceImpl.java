package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.element.service.ElementServiceAbs;
import ru.practicum.shareit.item.exception.CommentAuthorHasNotBookedItemException;
import ru.practicum.shareit.item.exception.ItemOwnerIsDifferentException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemDtoWithBookings;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.model.CommentMapper.toComment;
import static ru.practicum.shareit.item.model.CommentMapper.toCommentDto;
import static ru.practicum.shareit.item.model.ItemMapper.toItem;
import static ru.practicum.shareit.item.model.ItemMapper.toItemDto;
import static ru.practicum.shareit.item.model.ItemMapper.toItemDtoList;
import static ru.practicum.shareit.item.model.ItemMapper.toItemWithBookingsDto;

@Slf4j
@Service
public class ItemServiceImpl extends ElementServiceAbs<Item> implements ItemService {
    public static final String ELEMENT_NAME = "вещь";
    protected final ItemRepository itemRepository;
    protected final UserService userService;
    protected final BookingRepository bookingRepository;
    protected final CommentRepository commentRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserService userService, BookingRepository bookingRepository, CommentRepository commentRepository) {
        super(ELEMENT_NAME, itemRepository);
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
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
    public ItemDtoWithBookings get(long itemId, long userId) {
        Item item = getElement(itemId);
        long ownerId = item.getOwner().getId();
        return toItemWithBookingsDto(
                item,
                userId != ownerId ? null : bookingRepository.getLastBooking(itemId),
                userId != ownerId ? null : bookingRepository.getNextBooking(itemId),
                commentRepository.findByItem_IdOrderById(itemId)
        );
    }

    @Override
    public List<ItemDtoWithBookings> getAll(long userId) {
        return itemRepository.findByOwner_IdOrderById(userId).stream()
                .map(item -> toItemWithBookingsDto(
                                item,
                                bookingRepository.getLastBooking(item.getId()),
                                bookingRepository.getNextBooking(item.getId()),
                                commentRepository.findByItem_IdOrderById(item.getId())
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        return toItemDtoList(itemRepository.search(text));
    }

    @Override
    public ItemDto add(long userId, ItemDto itemDto) {
        User owner = userService.getElement(userId);

        Item item = toItem(new Item(), itemDto);
        item.setOwner(owner);

        Item itemAdded = itemRepository.save(item);
        log.info("add: " + itemAdded);
        return toItemDto(itemAdded);
    }

    @Override
    public CommentDto addComment(long itemId, long userId, CommentDto commentDto) {
        checkUserHasBookedItemInPast(userId, itemId);
        Item item = getElement(itemId);
        User author = userService.getElement(userId);

        Comment comment = toComment(new Comment(), commentDto);
        comment.setItem(item);
        comment.setAuthor(author);

        Comment commentAdded = commentRepository.save(comment);
        log.info("addComment: " + commentAdded);
        return toCommentDto(commentAdded);
    }

    @Override
    public ItemDto update(long itemId, long userId, ItemDto itemDto) {
        Item item = toItem(getElement(itemId), itemDto);
        checkItemBelongsToUser(item, userId);
        Item itemUpdated = itemRepository.save(item);
        log.info("update: " + itemUpdated);
        return toItemDto(itemUpdated);
    }
}
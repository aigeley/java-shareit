package ru.practicum.shareit.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.exception.BookingBookerSameAsOwnerException;
import ru.practicum.shareit.booking.exception.BookingIsAlreadyApprovedException;
import ru.practicum.shareit.booking.exception.BookingItemIsUnavailableException;
import ru.practicum.shareit.booking.exception.BookingUserIsDifferentException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingDtoMapper;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDtoMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.element.model.PageRequestFromElement;
import ru.practicum.shareit.element.service.ElementServiceAbs;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@Service
public class BookingServiceImpl extends ElementServiceAbs<Booking> implements BookingService {
    public static final String ELEMENT_NAME = "бронирование";
    private final BookingRepository bookingRepository;
    @Autowired
    private BookingDtoMapper bookingDtoMapper;
    @Autowired
    private BookingWithEntitiesDtoMapper bookingWithEntitiesDtoMapper;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        super(ELEMENT_NAME, bookingRepository);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void checkUserIsBookerOrOwner(Booking booking, long userId) {
        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new BookingUserIsDifferentException(elementName, booking.getId(), userId);
        }
    }

    @Override
    public void checkItemIsAvailable(Item item) {
        if (!item.getAvailable()) {
            throw new BookingItemIsUnavailableException(item.getId());
        }
    }

    @Override
    public void checkBookingIsAlreadyApproved(Booking booking) {
        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new BookingIsAlreadyApprovedException(elementName, booking.getId());
        }
    }

    @Override
    public void checkBookerIsNotOwner(long ownerId, long bookerId, long itemId) {
        if (ownerId == bookerId) {
            throw new BookingBookerSameAsOwnerException(ownerId, itemId);
        }
    }

    @Override
    public BookingWithEntitiesDto get(Long bookingId, Long userId) {
        userService.getAndCheckElement(userId);
        Booking booking = getAndCheckElement(bookingId);
        checkUserIsBookerOrOwner(booking, userId);
        return bookingWithEntitiesDtoMapper.toDto(booking);
    }

    @Override
    public List<BookingWithEntitiesDto> getAllByBooker(Integer from, Integer size, BookingState state, Long userId) {
        User booker = userService.getAndCheckElement(userId);
        return bookingWithEntitiesDtoMapper.toDtoList(
                bookingRepository.getAllByBooker(
                        state,
                        booker.getId(),
                        PageRequestFromElement.ofSortByIdDesc(from, size)
                )
        );
    }

    @Override
    public List<BookingWithEntitiesDto> getAllByOwner(Integer from, Integer size, BookingState state, Long userId) {
        User owner = userService.getAndCheckElement(userId);
        return bookingWithEntitiesDtoMapper.toDtoList(
                bookingRepository.getAllByOwner(
                        state,
                        owner.getId(),
                        PageRequestFromElement.ofSortByIdDesc(from, size)
                )
        );
    }

    @Override
    public BookingWithEntitiesDto add(Long userId, BookingDto bookingDto) {
        Item item = itemService.getAndCheckElement(bookingDto.getItemId());
        checkBookerIsNotOwner(userId, item.getOwner().getId(), item.getId());
        checkItemIsAvailable(item);
        User booker = userService.getAndCheckElement(userId);

        Booking booking = bookingDtoMapper.toElement(new Booking(), bookingDto);
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(BookingStatus.WAITING); //новое бронирование всегда создаётся в статусе WAITING

        Booking bookingAdded = bookingRepository.save(booking);
        log.info("add: " + bookingAdded);
        return bookingWithEntitiesDtoMapper.toDto(bookingAdded);
    }

    @Override
    public BookingWithEntitiesDto approve(Long bookingId, Boolean approved, Long userId) {
        userService.getAndCheckElement(userId);
        Booking booking = getAndCheckElement(bookingId);
        checkBookingIsAlreadyApproved(booking);
        itemService.checkItemBelongsToUser(booking.getItem(), userId);

        if (approved != null) {
            booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        }

        Booking bookingUpdated = bookingRepository.save(booking);
        log.info("approve: " + bookingUpdated);
        return bookingWithEntitiesDtoMapper.toDto(bookingUpdated);
    }
}

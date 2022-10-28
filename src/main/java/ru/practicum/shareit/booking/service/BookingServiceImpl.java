package ru.practicum.shareit.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.exception.BookingBookerSameAsOwnerException;
import ru.practicum.shareit.booking.exception.BookingIsAlreadyApprovedException;
import ru.practicum.shareit.booking.exception.BookingItemIsUnavailableException;
import ru.practicum.shareit.booking.exception.BookingUserIsDifferentException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingDtoWithEntities;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.element.service.ElementServiceAbs;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static ru.practicum.shareit.booking.model.BookingMapper.toBooking;
import static ru.practicum.shareit.booking.model.BookingMapper.toBookingOutDto;
import static ru.practicum.shareit.booking.model.BookingMapper.toBookingOutDtoList;

@Slf4j
@Service
public class BookingServiceImpl extends ElementServiceAbs<Booking> implements BookingService {
    public static final String ELEMENT_NAME = "бронирование";
    protected final BookingRepository bookingRepository;
    protected final ItemService itemService;
    protected final UserService userService;

    public BookingServiceImpl(BookingRepository bookingRepository, ItemService itemService, UserService userService) {
        super(ELEMENT_NAME, bookingRepository);
        this.bookingRepository = bookingRepository;
        this.itemService = itemService;
        this.userService = userService;
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
    public BookingDtoWithEntities get(long bookingId, long userId) {
        Booking booking = getElement(bookingId);
        checkUserIsBookerOrOwner(booking, userId);
        return toBookingOutDto(booking);
    }

    @Override
    public List<BookingDtoWithEntities> getAllByBooker(BookingState state, long userId) {
        User booker = userService.getElement(userId);
        return toBookingOutDtoList(bookingRepository.getAllByBooker(state, booker.getId()));
    }

    @Override
    public List<BookingDtoWithEntities> getAllByOwner(BookingState state, long userId) {
        User owner = userService.getElement(userId);
        return toBookingOutDtoList(bookingRepository.getAllByOwner(state, owner.getId()));
    }

    @Override
    public BookingDtoWithEntities add(long userId, BookingDto bookingDto) {
        Item item = itemService.getElement(bookingDto.getItemId());
        checkBookerIsNotOwner(userId, item.getOwner().getId(), item.getId());
        checkItemIsAvailable(item);
        User booker = userService.getElement(userId);

        //новое бронирование всегда создаётся в статусе WAITING
        Booking booking = toBooking(new Booking(), bookingDto, item, booker, BookingStatus.WAITING);

        Booking bookingAdded = bookingRepository.save(booking);
        log.info("add: " + bookingAdded);
        return toBookingOutDto(bookingAdded);
    }

    @Override
    public BookingDtoWithEntities approve(long bookingId, Boolean approved, long userId) {
        Booking booking = getElement(bookingId);
        checkBookingIsAlreadyApproved(booking);
        itemService.checkItemBelongsToUser(booking.getItem(), userId);

        if (approved != null) {
            booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        }

        Booking bookingUpdated = bookingRepository.save(booking);
        log.info("update: " + bookingUpdated);
        return toBookingOutDto(bookingUpdated);
    }
}

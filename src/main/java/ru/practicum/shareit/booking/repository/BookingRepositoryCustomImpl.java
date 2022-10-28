package ru.practicum.shareit.booking.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.List;

public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {
    public static Specification<Booking> bookingBookerIs(long userId) {
        return (root, query, builder) -> builder.equal(root.get("booker").get("id"), userId);
    }

    public static Specification<Booking> bookingItemOwnerIs(long userId) {
        return (root, query, builder) -> builder.equal(root.get("item").get("owner").get("id"), userId);
    }

    public static Specification<Booking> bookingItemIs(long itemId) {
        return (root, query, builder) -> builder.equal(root.get("item").get("id"), itemId);
    }

    public static Specification<Booking> bookingStatusIs(BookingStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

    public static Specification<Booking> bookingIsCurrent() {
        return (root, query, builder) ->
                builder.between(builder.currentTimestamp(), root.get("start"), root.get("end"));
    }

    public static Specification<Booking> bookingIsPast() {
        return (root, query, builder) -> builder.greaterThan(builder.currentTimestamp(), root.get("end"));
    }

    public static Specification<Booking> bookingIsFuture() {
        return (root, query, builder) -> builder.lessThan(builder.currentTimestamp(), root.get("start"));
    }

    private final BookingRepository bookingRepository;

    public BookingRepositoryCustomImpl(@Lazy BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    private List<Booking> getAll(BookingState state, Specification<Booking> initialCondition) {
        Specification<Booking> condition;

        switch (state) {
            case CURRENT:
                condition = bookingIsCurrent();
                break;
            case PAST:
                condition = bookingIsPast();
                break;
            case FUTURE:
                condition = bookingIsFuture();
                break;
            case WAITING:
                condition = bookingStatusIs(BookingStatus.WAITING);
                break;
            case REJECTED:
                condition = bookingStatusIs(BookingStatus.REJECTED);
                break;
            default:
                condition = null;
                break;
        }

        Specification<Booking> specification = Specification
                .where(initialCondition)
                .and(condition);

        return bookingRepository.findAll(specification, Sort.by(Order.desc("id")));
    }

    @Override
    public List<Booking> getAllByBooker(BookingState state, long userId) {
        return getAll(state, bookingBookerIs(userId));
    }

    @Override
    public List<Booking> getAllByOwner(BookingState state, long userId) {
        return getAll(state, bookingItemOwnerIs(userId));
    }

    private Booking getTopBooking(Specification<Booking> specification, Sort sort) {
        return bookingRepository.findAll(specification, PageRequest.of(0, 1, sort))
                .getContent()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Booking getLastBooking(long itemId) {
        Specification<Booking> specification = Specification
                .where(bookingItemIs(itemId))
                .and(bookingIsPast());

        return getTopBooking(specification, Sort.by(Order.desc("start")));
    }

    @Override
    public Booking getNextBooking(long itemId) {
        Specification<Booking> specification = Specification
                .where(bookingItemIs(itemId))
                .and(bookingIsFuture());

        return getTopBooking(specification, Sort.by(Order.desc("start")));
    }

    @Override
    public boolean hasUserBookedItemInPast(long userId, long itemId) {
        Specification<Booking> specification = Specification
                .where(bookingBookerIs(userId))
                .and(bookingItemIs(itemId))
                .and(bookingIsPast());

        return bookingRepository.exists(specification);
    }
}
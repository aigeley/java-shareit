package ru.practicum.shareit.booking;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.booking.model.validation.BookingStateIsCorrect;
import ru.practicum.shareit.element.ElementControllerAbs;
import ru.practicum.shareit.element.model.Create;

import java.util.List;

import static ru.practicum.shareit.booking.BookingController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@Validated
public class BookingController extends ElementControllerAbs {
    public static final String BASE_PATH = "/bookings";
    private final BookingClient bookingClient;

    public BookingController(BookingClient bookingClient) {
        super(bookingClient);
        this.bookingClient = bookingClient;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public BookingWithEntitiesDto get(
            @PathVariable("id") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingClient.get(bookingId, userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<BookingWithEntitiesDto> getAllByBooker(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @BookingStateIsCorrect @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingClient.getAllByBooker(from, size, BookingState.valueOf(StringUtils.upperCase(state)), userId);
    }

    @GetMapping(path = "/owner", produces = "application/json;charset=UTF-8")
    public List<BookingWithEntitiesDto> getAllByOwner(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @BookingStateIsCorrect @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingClient.getAllByOwner(from, size, BookingState.valueOf(StringUtils.upperCase(state)), userId);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public BookingWithEntitiesDto add(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Validated({Create.class}) @RequestBody BookingDto bookingDto
    ) {
        return bookingClient.add(userId, bookingDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public BookingWithEntitiesDto approve(
            @PathVariable("id") Long bookingId,
            @RequestParam Boolean approved,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingClient.approve(bookingId, approved, userId);
    }
}

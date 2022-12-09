package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.element.ElementControllerAbs;

import java.util.List;

import static ru.practicum.shareit.booking.BookingController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class BookingController extends ElementControllerAbs<Booking> {
    public static final String BASE_PATH = "/bookings";
    private final BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService) {
        super(bookingService);
        this.bookingService = bookingService;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public BookingWithEntitiesDto get(
            @PathVariable("id") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.get(bookingId, userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<BookingWithEntitiesDto> getAllByBooker(
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size,
            @RequestParam String state,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.getAllByBooker(from, size, BookingState.valueOf(state), userId);
    }

    @GetMapping(path = "/owner", produces = "application/json;charset=UTF-8")
    public List<BookingWithEntitiesDto> getAllByOwner(
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer size,
            @RequestParam String state,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.getAllByOwner(from, size, BookingState.valueOf(state), userId);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public BookingWithEntitiesDto add(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody BookingDto bookingDto
    ) {
        return bookingService.add(userId, bookingDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public BookingWithEntitiesDto approve(
            @PathVariable("id") Long bookingId,
            @RequestParam Boolean approved,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.approve(bookingId, approved, userId);
    }
}

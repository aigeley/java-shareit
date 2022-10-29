package ru.practicum.shareit.booking;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingDtoWithEntities;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.validation.BookingStateIsCorrect;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.element.model.Create;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Validated
public class BookingController {
    BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public BookingDtoWithEntities get(
            @PathVariable("id") long bookingId,
            @RequestHeader("X-Sharer-User-Id") long userId
    ) {
        return bookingService.get(bookingId, userId);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<BookingDtoWithEntities> getAll(
            @BookingStateIsCorrect @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader("X-Sharer-User-Id") long userId
    ) {
        return bookingService.getAllByBooker(BookingState.valueOf(state), userId);
    }

    @GetMapping(path = "/owner", produces = "application/json;charset=UTF-8")
    public List<BookingDtoWithEntities> getAllByOwner(
            @BookingStateIsCorrect @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader("X-Sharer-User-Id") long userId
    ) {
        return bookingService.getAllByOwner(BookingState.valueOf(state), userId);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public BookingDtoWithEntities add(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @Validated({Create.class}) @RequestBody BookingDto bookingDto
    ) {
        return bookingService.add(userId, bookingDto);
    }

    @PatchMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public BookingDtoWithEntities approve(
            @PathVariable("id") long bookingId,
            @RequestParam Boolean approved,
            @RequestHeader("X-Sharer-User-Id") long userId
    ) {
        return bookingService.approve(bookingId, approved, userId);
    }

    @DeleteMapping
    public void deleteAll() {
        bookingService.deleteAll();
    }
}

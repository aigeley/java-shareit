package ru.practicum.shareit.booking.model;

import lombok.Value;
import ru.practicum.shareit.booking.model.validation.BookingStartLessThenEnd;
import ru.practicum.shareit.element.model.Create;
import ru.practicum.shareit.element.model.Update;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@BookingStartLessThenEnd(groups = {Create.class, Update.class})
public class BookingDto {
    /**
     * уникальный идентификатор бронирования
     */
    Long id;
    /**
     * дата и время начала бронирования
     */
    @FutureOrPresent(groups = {Create.class, Update.class})
    @NotNull(groups = {Create.class})
    LocalDateTime start;
    /**
     * дата и время конца бронирования
     */
    @FutureOrPresent(groups = {Create.class, Update.class})
    @NotNull(groups = {Create.class})
    LocalDateTime end;
    /**
     * вещь, которую пользователь бронирует
     */
    @NotNull(groups = {Create.class})
    Long itemId;
    /**
     * пользователь, который осуществляет бронирование
     */
    Long bookerId;
    /**
     * статус бронирования
     */
    BookingStatus status;
}

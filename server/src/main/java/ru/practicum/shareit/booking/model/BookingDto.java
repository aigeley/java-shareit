package ru.practicum.shareit.booking.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Element;

import java.time.LocalDateTime;

@Value
@ToString
public class BookingDto extends Element {
    /**
     * уникальный идентификатор бронирования
     */
    Long id;
    /**
     * дата и время начала бронирования
     */
    LocalDateTime start;
    /**
     * дата и время конца бронирования
     */
    LocalDateTime end;
    /**
     * вещь, которую пользователь бронирует
     */
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

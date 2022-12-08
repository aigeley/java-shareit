package ru.practicum.shareit.booking.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Element;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.user.model.UserDto;

import java.time.LocalDateTime;

@Value
@ToString
public class BookingWithEntitiesDto extends Element {
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
    ItemDto item;
    /**
     * пользователь, который осуществляет бронирование
     */
    UserDto booker;
    /**
     * статус бронирования
     */
    BookingStatus status;
}

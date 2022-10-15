package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor
public class BookingDto {
    long id; //уникальный идентификатор бронирования;
    ZonedDateTime start; //дата и время начала бронирования;
    ZonedDateTime end; //дата и время конца бронирования;
    Item item; //вещь, которую пользователь бронирует;
    User booker; //пользователь, который осуществляет бронирование;
    Status status; //статус бронирования.
}

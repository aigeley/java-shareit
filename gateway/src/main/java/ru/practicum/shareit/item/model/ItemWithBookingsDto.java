package ru.practicum.shareit.item.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.element.model.Identifiable;

import java.util.List;

@Value
@ToString
public class ItemWithBookingsDto implements Identifiable {
    /**
     * уникальный идентификатор вещи
     */
    Long id;
    /**
     * краткое название
     */
    String name;
    /**
     * развёрнутое описание
     */
    String description;
    /**
     * статус о том, доступна или нет вещь для аренды
     */
    Boolean available;
    /**
     * владелец вещи
     */
    Long ownerId;
    /**
     * если вещь была создана по запросу другого пользователя,
     * то в этом поле будет храниться ссылка на соответствующий запрос
     */
    Long requestId;
    /**
     * последнее бронирование
     */
    BookingDto lastBooking;
    /**
     * ближайшее следующее бронирование
     */
    BookingDto nextBooking;
    /**
     * комментарии
     */
    List<CommentDto> comments;
}

package ru.practicum.shareit.item.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.element.model.Element;

import java.util.List;

@Value
@ToString
public class ItemWithBookings extends Element {
    /**
     * вещь
     */
    Item item;
    /**
     * последнее бронирование
     */
    Booking lastBooking;
    /**
     * ближайшее следующее бронирование
     */
    Booking nextBooking;
    /**
     * комментарии
     */
    List<Comment> comments;

    @Override
    public Long getId() {
        return item.getId();
    }

    @Override
    public void setId(Long id) {
        item.setId(id);
    }
}

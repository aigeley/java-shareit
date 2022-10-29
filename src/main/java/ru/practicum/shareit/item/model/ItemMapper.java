package ru.practicum.shareit.item.model;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.model.BookingMapper.toBookingDto;
import static ru.practicum.shareit.item.model.CommentMapper.toCommentDtoList;

public class ItemMapper {
    private ItemMapper() {
    }

    public static ItemDto toItemDto(Item item) {
        return item == null ? null : new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                Optional.ofNullable(item.getRequest()).map(ItemRequest::getId).orElse(null)
        );
    }

    public static ItemDtoWithBookings toItemWithBookingsDto(Item item, Booking lastBooking, Booking nextBooking,
                                                            List<Comment> commentsList) {
        return item == null ? null : new ItemDtoWithBookings(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                Optional.ofNullable(item.getRequest()).map(ItemRequest::getId).orElse(null),
                toBookingDto(lastBooking),
                toBookingDto(nextBooking),
                toCommentDtoList(commentsList)
        );
    }

    public static List<ItemDto> toItemDtoList(List<Item> itemsList) {
        return itemsList == null ? Collections.emptyList() : itemsList.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public static Item toItem(Item item, ItemDto itemDto) {
        Optional.ofNullable(itemDto.getName()).ifPresent(item::setName);
        Optional.ofNullable(itemDto.getDescription()).ifPresent(item::setDescription);
        Optional.ofNullable(itemDto.getAvailable()).ifPresent(item::setAvailable);
        return item;
    }
}

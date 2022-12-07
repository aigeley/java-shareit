package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.BookingDtoMapper;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Component
public class ItemWithBookingsDtoMapper extends ElementDtoMapperAbs<ItemWithBookings, ItemWithBookingsDto> {
    private final BookingDtoMapper bookingDtoMapper;
    private final CommentDtoMapper commentDtoMapper;

    public ItemWithBookingsDtoMapper(BookingDtoMapper bookingDtoMapper, CommentDtoMapper commentDtoMapper) {
        super(
                ItemWithBookingsDto.class,
                new TypeReference<>() {
                }
        );
        this.bookingDtoMapper = bookingDtoMapper;
        this.commentDtoMapper = commentDtoMapper;
    }

    @Override
    public ItemWithBookingsDto toDto(ItemWithBookings itemWithBookings) {
        return itemWithBookings == null ? null : new ItemWithBookingsDto(
                itemWithBookings.getItem().getId(),
                itemWithBookings.getItem().getName(),
                itemWithBookings.getItem().getDescription(),
                itemWithBookings.getItem().getAvailable(),
                Optional.ofNullable(itemWithBookings.getItem().getOwner())
                        .map(User::getId)
                        .orElse(null),
                Optional.ofNullable(itemWithBookings.getItem().getRequest())
                        .map(ItemRequest::getId)
                        .orElse(null),
                bookingDtoMapper.toDto(itemWithBookings.getLastBooking()),
                bookingDtoMapper.toDto(itemWithBookings.getNextBooking()),
                commentDtoMapper.toDtoList(itemWithBookings.getComments())
        );
    }
}

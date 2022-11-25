package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.item.model.ItemDtoMapper;
import ru.practicum.shareit.user.model.UserDtoMapper;

@Component
public class BookingWithEntitiesDtoMapper extends ElementDtoMapperAbs<Booking, BookingWithEntitiesDto> {
    @Autowired
    private ItemDtoMapper itemDtoMapper;
    @Autowired
    private UserDtoMapper userDtoMapper;

    public BookingWithEntitiesDtoMapper() {
        super(
                BookingWithEntitiesDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public BookingWithEntitiesDto toDto(Booking booking) {
        return booking == null ? null : new BookingWithEntitiesDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                itemDtoMapper.toDto(booking.getItem()),
                userDtoMapper.toDto(booking.getBooker()),
                booking.getStatus()
        );
    }
}

package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.item.model.ItemWithBookings;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;

@Component
public class ItemWithBookingsTestUtils extends ElementTestUtils<ItemWithBookings, ItemWithBookingsDto> {
    public ItemWithBookingsTestUtils(ElementDtoMapperAbs<ItemWithBookings, ItemWithBookingsDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

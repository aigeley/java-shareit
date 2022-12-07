package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;

@Component
public class ItemTestUtils extends ElementTestUtils<Item, ItemDto> {
    public ItemTestUtils(ElementDtoMapperAbs<Item, ItemDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

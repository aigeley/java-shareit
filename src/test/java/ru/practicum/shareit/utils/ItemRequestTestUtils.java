package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;

@Component
public class ItemRequestTestUtils extends ElementTestUtils<ItemRequest, ItemRequestDto> {
    public ItemRequestTestUtils(ElementDtoMapperAbs<ItemRequest, ItemRequestDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

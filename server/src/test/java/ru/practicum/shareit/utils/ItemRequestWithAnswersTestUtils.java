package ru.practicum.shareit.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.request.model.ItemRequestWithAnswers;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;

@Component
public class ItemRequestWithAnswersTestUtils extends ElementTestUtils<ItemRequestWithAnswers, ItemRequestWithAnswersDto> {
    public ItemRequestWithAnswersTestUtils(ElementDtoMapperAbs<ItemRequestWithAnswers, ItemRequestWithAnswersDto> elementDtoMapper) {
        super(elementDtoMapper);
    }
}

package ru.practicum.shareit.request.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.item.model.ItemDtoMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Component
public class ItemRequestWithAnswersDtoMapper
        extends ElementDtoMapperAbs<ItemRequestWithAnswers, ItemRequestWithAnswersDto> {
    private final ItemDtoMapper itemDtoMapper;

    public ItemRequestWithAnswersDtoMapper(ItemDtoMapper itemDtoMapper) {
        super(
                ItemRequestWithAnswersDto.class,
                new TypeReference<>() {
                }
        );
        this.itemDtoMapper = itemDtoMapper;
    }

    @Override
    public ItemRequestWithAnswersDto toDto(ItemRequestWithAnswers itemRequestWithAnswers) {
        return itemRequestWithAnswers == null ? null : new ItemRequestWithAnswersDto(
                itemRequestWithAnswers.getItemRequest().getId(),
                itemRequestWithAnswers.getItemRequest().getDescription(),
                Optional.ofNullable(itemRequestWithAnswers.getItemRequest().getRequestor())
                        .map(User::getId)
                        .orElse(null),
                itemRequestWithAnswers.getItemRequest().getCreated(),
                itemDtoMapper.toDtoList(itemRequestWithAnswers.getItems())
        );
    }
}

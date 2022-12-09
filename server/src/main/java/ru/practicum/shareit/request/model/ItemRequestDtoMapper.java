package ru.practicum.shareit.request.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Component
public class ItemRequestDtoMapper extends ElementDtoMapperAbs<ItemRequest, ItemRequestDto> {
    public ItemRequestDtoMapper() {
        super(
                ItemRequestDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public ItemRequestDto toDto(ItemRequest itemRequest) {
        return itemRequest == null ? null : new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                Optional.ofNullable(itemRequest.getRequestor()).map(User::getId).orElse(null),
                itemRequest.getCreated()
        );
    }

    @Override
    public ItemRequest toElement(ItemRequest itemRequest, ItemRequestDto itemRequestDto) {
        itemRequest.setDescription(itemRequestDto.getDescription());
        return itemRequest;
    }
}

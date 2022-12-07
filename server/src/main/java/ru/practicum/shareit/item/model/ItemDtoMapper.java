package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Component
public class ItemDtoMapper extends ElementDtoMapperAbs<Item, ItemDto> {
    public ItemDtoMapper() {
        super(
                ItemDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public ItemDto toDto(Item item) {
        return item == null ? null : new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                Optional.ofNullable(item.getOwner()).map(User::getId).orElse(null),
                Optional.ofNullable(item.getRequest()).map(ItemRequest::getId).orElse(null)
        );
    }

    @Override
    public Item toElement(Item item, ItemDto itemDto) {
        Optional.ofNullable(itemDto.getName()).ifPresent(item::setName);
        Optional.ofNullable(itemDto.getDescription()).ifPresent(item::setDescription);
        Optional.ofNullable(itemDto.getAvailable()).ifPresent(item::setAvailable);
        return item;
    }
}

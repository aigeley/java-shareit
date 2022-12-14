package ru.practicum.shareit.request.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Element;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Value
@ToString
public class ItemRequestWithAnswers extends Element {
    /**
     * запрос
     */
    ItemRequest itemRequest;
    /**
     * список ответов на запрос
     */
    List<Item> items;

    @Override
    public Long getId() {
        return itemRequest.getId();
    }

    @Override
    public void setId(Long id) {
        itemRequest.setId(id);
    }
}

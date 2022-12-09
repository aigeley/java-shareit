package ru.practicum.shareit.item.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.client.ElementClientAbs;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;

import java.util.List;
import java.util.Map;

@Service
public class ItemClient extends ElementClientAbs {
    private final ItemEntity itemEntity;
    private final CommentEntity commentEntity;
    private final Class<ItemWithBookingsDto> itemWithBookingsDtoClass;
    private final ParameterizedTypeReference<List<ItemWithBookingsDto>> itemWithBookingsDtoListType;
    private final Class<ItemDto> itemDtoClass;
    private final ParameterizedTypeReference<List<ItemDto>> itemDtoListType;
    private final Class<CommentDto> commentDtoClass;

    public ItemClient(
            @Value("${shareit-server.url}") String serverUrl,
            RestTemplateBuilder builder,
            ItemEntity itemEntity,
            CommentEntity commentEntity) {
        super(
                builder,
                serverUrl,
                ItemController.BASE_PATH
        );
        this.itemEntity = itemEntity;
        this.commentEntity = commentEntity;
        this.itemWithBookingsDtoClass = ItemWithBookingsDto.class;
        this.itemWithBookingsDtoListType = new ParameterizedTypeReference<>() {
        };
        this.itemDtoClass = ItemDto.class;
        this.itemDtoListType = new ParameterizedTypeReference<>() {
        };
        this.commentDtoClass = CommentDto.class;
    }

    public ItemWithBookingsDto get(Long itemId, Long userId) {
        return rest.exchange("/" + itemId, HttpMethod.GET, itemEntity.getEntity(null, userId), itemWithBookingsDtoClass)
                .getBody();
    }

    public List<ItemWithBookingsDto> getAll(Integer from, Integer size, Long userId) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );

        return rest.exchange("?from={from}&size={size}", HttpMethod.GET, itemEntity.getEntity(null, userId),
                        itemWithBookingsDtoListType, parameters)
                .getBody();
    }

    public List<ItemDto> search(Integer from, Integer size, String text, Long userId) {
        Map<String, Object> parameters = Map.of(
                "text", text,
                "from", from,
                "size", size
        );

        return rest.exchange("/search?text={text}&from={from}&size={size}", HttpMethod.GET,
                        itemEntity.getEntity(null, userId), itemDtoListType, parameters)
                .getBody();
    }

    public ItemDto add(Long userId, ItemDto itemDto) {
        return rest.exchange("", HttpMethod.POST, itemEntity.getEntity(itemDto, userId), itemDtoClass).getBody();
    }

    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto) {
        return rest.exchange("/" + itemId + "/comment", HttpMethod.POST, commentEntity.getEntity(commentDto, userId),
                        commentDtoClass)
                .getBody();
    }

    public ItemDto update(Long itemId, Long userId, ItemDto itemDto) {
        return rest.exchange("/" + itemId, HttpMethod.PATCH, itemEntity.getEntity(itemDto, userId), itemDtoClass)
                .getBody();
    }
}

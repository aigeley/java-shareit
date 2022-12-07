package ru.practicum.shareit.request.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.client.ElementClientAbs;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;

import java.util.List;
import java.util.Map;

@Service
public class ItemRequestClient extends ElementClientAbs {
    private final ItemRequestEntity itemRequestEntity;
    private final Class<ItemRequestWithAnswersDto> itemRequestWithAnswersDtoClass;
    private final ParameterizedTypeReference<List<ItemRequestWithAnswersDto>> itemRequestWithAnswersDtoListType;
    private final Class<ItemRequestDto> itemRequestDtoClass;

    public ItemRequestClient(
            @Value("${shareit-server.url}") String serverUrl,
            RestTemplateBuilder builder,
            ItemRequestEntity itemRequestEntity
    ) {
        super(
                builder,
                serverUrl,
                ItemRequestController.BASE_PATH
        );
        this.itemRequestEntity = itemRequestEntity;
        itemRequestWithAnswersDtoClass = ItemRequestWithAnswersDto.class;
        itemRequestWithAnswersDtoListType = new ParameterizedTypeReference<>() {
        };
        itemRequestDtoClass = ItemRequestDto.class;
    }

    public ItemRequestWithAnswersDto get(Long itemRequestId, Long userId) {
        return rest.exchange("/" + itemRequestId, HttpMethod.GET, itemRequestEntity.getEntity(null, userId),
                        itemRequestWithAnswersDtoClass)
                .getBody();
    }

    public List<ItemRequestWithAnswersDto> getAllByCurrentUser(Long userId) {
        return rest.exchange("", HttpMethod.GET, itemRequestEntity.getEntity(null, userId),
                        itemRequestWithAnswersDtoListType)
                .getBody();
    }

    public List<ItemRequestWithAnswersDto> getAllByOtherUsers(Integer from, Integer size, Long userId) {
        if (from == null || size == null) {
            return rest.exchange("/all", HttpMethod.GET, itemRequestEntity.getEntity(null, userId),
                            itemRequestWithAnswersDtoListType)
                    .getBody();
        } else {
            Map<String, Object> parameters = Map.of(
                    "from", from,
                    "size", size
            );
            return rest.exchange("/all?from={from}&size={size}", HttpMethod.GET,
                            itemRequestEntity.getEntity(null, userId), itemRequestWithAnswersDtoListType, parameters)
                    .getBody();
        }
    }

    public ItemRequestDto add(Long userId, ItemRequestDto itemRequestDto) {
        return rest.exchange("", HttpMethod.POST, itemRequestEntity.getEntity(itemRequestDto, userId),
                        itemRequestDtoClass)
                .getBody();
    }
}

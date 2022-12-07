package ru.practicum.shareit.booking.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.element.client.ElementClientAbs;

import java.util.List;
import java.util.Map;

@Service
public class BookingClient extends ElementClientAbs {
    private final BookingEntity bookingEntity;
    private final Class<BookingWithEntitiesDto> bookingWithEntitiesDtoClass;
    private final ParameterizedTypeReference<List<BookingWithEntitiesDto>> bookingWithEntitiesDtoListType;

    public BookingClient(
            @Value("${shareit-server.url}") String serverUrl,
            RestTemplateBuilder builder,
            BookingEntity bookingEntity
    ) {
        super(
                builder,
                serverUrl,
                BookingController.BASE_PATH
        );
        this.bookingEntity = bookingEntity;
        bookingWithEntitiesDtoClass = BookingWithEntitiesDto.class;
        bookingWithEntitiesDtoListType = new ParameterizedTypeReference<>() {
        };
    }

    public BookingWithEntitiesDto get(Long bookingId, Long userId) {
        return rest.exchange("/" + bookingId, HttpMethod.GET, bookingEntity.getEntity(null, userId),
                        bookingWithEntitiesDtoClass)
                .getBody();
    }

    public List<BookingWithEntitiesDto> getAllByBooker(Integer from, Integer size, BookingState state, Long userId) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );

        return rest.exchange("?state={state}&from={from}&size={size}", HttpMethod.GET, bookingEntity.getEntity(null, userId), bookingWithEntitiesDtoListType,
                        parameters)
                .getBody();
    }

    public List<BookingWithEntitiesDto> getAllByOwner(Integer from, Integer size, BookingState state, Long userId) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );

        return rest.exchange("/owner?state={state}&from={from}&size={size}", HttpMethod.GET, bookingEntity.getEntity(null, userId),
                        bookingWithEntitiesDtoListType, parameters)
                .getBody();
    }

    public BookingWithEntitiesDto add(Long userId, BookingDto bookingDto) {
        return rest.exchange("", HttpMethod.POST, bookingEntity.getEntity(bookingDto, userId),
                        bookingWithEntitiesDtoClass)
                .getBody();
    }

    public BookingWithEntitiesDto approve(Long bookingId, Boolean approved, Long userId) {
        Map<String, Object> parameters = Map.of(
                "approved", approved
        );

        return rest.exchange("/" + bookingId + "?approved={approved}", HttpMethod.PATCH, bookingEntity.getEntity(null, userId),
                        bookingWithEntitiesDtoClass, parameters)
                .getBody();
    }
}

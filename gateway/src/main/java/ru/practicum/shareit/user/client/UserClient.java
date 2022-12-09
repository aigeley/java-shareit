package ru.practicum.shareit.user.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.client.ElementClientAbs;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

@Service
public class UserClient extends ElementClientAbs {
    private final UserEntity userEntity;
    private final Class<UserDto> userDtoClass;
    private final ParameterizedTypeReference<List<UserDto>> userDtoListType;

    public UserClient(
            @Value("${shareit-server.url}") String serverUrl,
            RestTemplateBuilder builder,
            UserEntity userEntity
    ) {
        super(
                builder,
                serverUrl,
                UserController.BASE_PATH
        );
        this.userEntity = userEntity;
        userDtoClass = UserDto.class;
        userDtoListType = new ParameterizedTypeReference<>() {
        };
    }

    public UserDto get(Long userId) {
        return rest.exchange("/" + userId, HttpMethod.GET, userEntity.getEntity(null, userId), userDtoClass).getBody();
    }

    public List<UserDto> getAll() {
        return rest.exchange("", HttpMethod.GET, userEntity.getEntity(null, null), userDtoListType).getBody();
    }

    public UserDto add(UserDto userDto) {
        return rest.exchange("", HttpMethod.POST, userEntity.getEntity(userDto, null), userDtoClass).getBody();
    }

    public UserDto update(Long userId, UserDto userDto) {
        return rest.exchange("/" + userId, HttpMethod.PATCH, userEntity.getEntity(userDto, null), userDtoClass)
                .getBody();
    }
}

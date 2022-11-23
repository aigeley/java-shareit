package ru.practicum.shareit.request.model;

import lombok.ToString;
import lombok.Value;
import ru.practicum.shareit.element.model.Create;
import ru.practicum.shareit.element.model.Identifiable;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Value
@ToString
public class ItemRequestDto implements Identifiable {
    /**
     * уникальный идентификатор запроса
     */
    Long id;
    /**
     * текст запроса, содержащий описание требуемой вещи
     */
    @NotBlank(groups = {Create.class})
    String description;
    /**
     * пользователь, создавший запрос
     */
    Long requestorId;
    /**
     * дата и время создания запроса
     */
    LocalDateTime created;
}

package ru.practicum.shareit.element.model;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;

import java.util.List;

public abstract class ElementDtoMapperAbs<E, D> implements ElementDtoMapper<E, D> {
    @Getter
    private final Class<D> dtoClass;
    @Getter
    private final TypeReference<List<D>> dtoListType;

    protected ElementDtoMapperAbs(Class<D> dtoClass, TypeReference<List<D>> dtoListType) {
        this.dtoClass = dtoClass;
        this.dtoListType = dtoListType;
    }
}

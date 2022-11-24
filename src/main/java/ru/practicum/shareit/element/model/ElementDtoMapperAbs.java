package ru.practicum.shareit.element.model;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ElementDtoMapperAbs<E, D> {
    @Getter
    private final Class<D> dtoClass;
    @Getter
    private final TypeReference<List<D>> dtoListType;

    protected ElementDtoMapperAbs(Class<D> dtoClass, TypeReference<List<D>> dtoListType) {
        this.dtoClass = dtoClass;
        this.dtoListType = dtoListType;
    }

    public abstract D toDto(E element);

    public List<D> toDtoList(List<E> elementList) {
        return elementList == null ? Collections.emptyList() : elementList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public E toElement(E element, D elementDto) {
        throw new NotImplementedException();
    }
}

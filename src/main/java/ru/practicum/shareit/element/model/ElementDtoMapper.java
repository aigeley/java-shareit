package ru.practicum.shareit.element.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface ElementDtoMapper<E, D> {
    Class<D> getDtoClass();

    TypeReference<List<D>> getDtoListType();

    D toDto(E element);

    default List<D> toDtoList(List<E> elementList) {
        return elementList == null ? Collections.emptyList() : elementList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default E toElement(E element, D elementDto) {
        throw new NotImplementedException();
    }
}

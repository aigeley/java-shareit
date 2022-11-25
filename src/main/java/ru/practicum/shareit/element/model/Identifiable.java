package ru.practicum.shareit.element.model;

import org.apache.commons.lang3.NotImplementedException;

public interface Identifiable {
    Long getId();

    default void setId(Long id) {
        throw new NotImplementedException();
    }
}

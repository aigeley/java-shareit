package ru.practicum.shareit.element.model;

import org.apache.commons.lang3.NotImplementedException;

public abstract class Element {
    public abstract Long getId();

    public void setId(Long id) {
        throw new NotImplementedException();
    }
}

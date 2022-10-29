package ru.practicum.shareit.element.service;

public interface ElementService<T> {
    T getElement(long id);

    void delete(long id);

    void deleteAll();
}

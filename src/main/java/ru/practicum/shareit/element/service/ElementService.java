package ru.practicum.shareit.element.service;

public interface ElementService<T> {
    T getAndCheckElement(long id);

    void delete(Long id);

    void deleteAll();
}

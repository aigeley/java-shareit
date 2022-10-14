package ru.practicum.shareit.element;

public interface ElementService<T> {
    T getElement(long id);

    void deleteElement(long id);

    void deleteAll();
}

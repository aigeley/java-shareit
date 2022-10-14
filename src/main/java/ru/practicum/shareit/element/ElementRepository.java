package ru.practicum.shareit.element;

public interface ElementRepository<T> {
    long getNextId();

    boolean isExists(long id);

    T get(long id);

    T add(T element);

    T update(T element);

    T delete(long id);

    void deleteAll();
}

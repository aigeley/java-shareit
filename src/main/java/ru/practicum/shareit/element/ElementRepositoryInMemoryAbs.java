package ru.practicum.shareit.element;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ElementRepositoryInMemoryAbs<T extends Identifiable> implements ElementRepository<T> {
    protected final Map<Long, T> elements;
    private final AtomicLong lastId;

    protected ElementRepositoryInMemoryAbs() {
        this.elements = new HashMap<>();
        this.lastId = new AtomicLong(0);
    }

    @Override
    public long getNextId() {
        return lastId.incrementAndGet();
    }

    @Override
    public boolean isExists(long id) {
        return elements.containsKey(id);
    }

    @Override
    public T get(long id) {
        return elements.get(id);
    }

    @Override
    public T add(T element) {
        elements.put(element.getId(), element);
        return element;
    }

    @Override
    public T update(T element) {
        return this.add(element);
    }

    @Override
    public T delete(long id) {
        return elements.remove(id);
    }

    @Override
    public void deleteAll() {
        elements.clear();
    }
}

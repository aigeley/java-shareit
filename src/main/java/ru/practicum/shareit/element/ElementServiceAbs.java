package ru.practicum.shareit.element;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.element.exception.ElementNotFoundException;

@Slf4j
public abstract class ElementServiceAbs<T> implements ElementService<T> {
    protected final String elementName;
    protected final ElementRepository<T> elementRepository;

    protected ElementServiceAbs(String elementName, ElementRepository<T> elementRepository) {
        this.elementName = elementName;
        this.elementRepository = elementRepository;
    }

    @Override
    public T getElement(long id) {
        T element = elementRepository.get(id);

        if (element == null) {
            throw new ElementNotFoundException(elementName, id);
        }

        return element;
    }

    @Override
    public void delete(long id) {
        if (elementRepository.delete(id) == null) {
            throw new ElementNotFoundException(elementName, id);
        }

        log.info("delete: {} с id = {}", elementName, id);
    }

    @Override
    public void deleteAll() {
        elementRepository.deleteAll();
        log.info("deleteAll: " + elementName);
    }
}

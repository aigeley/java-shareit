package ru.practicum.shareit.element;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.element.exception.ElementNotFoundException;

@Slf4j
public abstract class ElementServiceAbs<T> {
    protected final String elementName;
    protected final ElementRepository<T> elementRepository;

    protected ElementServiceAbs(String elementName, ElementRepository<T> elementRepository) {
        this.elementName = elementName;
        this.elementRepository = elementRepository;
    }

    public T getElement(long id) {
        T element = elementRepository.get(id);

        if (element == null) {
            throw new ElementNotFoundException(elementName, id);
        }

        return element;
    }

    public void deleteElement(long id) {
        if (elementRepository.delete(id) == null) {
            throw new ElementNotFoundException(elementName, id);
        }

        log.info("delete: {} с id = {}", elementName, id);
    }

    public void deleteAll() {
        elementRepository.deleteAll();
        log.info("deleteAll: " + elementName);
    }
}

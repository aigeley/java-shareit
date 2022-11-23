package ru.practicum.shareit.element.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.element.exception.ElementNotFoundException;

@Slf4j
public abstract class ElementServiceAbs<E> implements ElementService<E> {
    protected final String elementName;
    protected final JpaRepository<E, Long> elementRepository;

    protected ElementServiceAbs(String elementName, JpaRepository<E, Long> elementRepository) {
        this.elementName = elementName;
        this.elementRepository = elementRepository;
    }

    @Override
    public E getAndCheckElement(long id) {
        return elementRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(elementName, id));
    }

    @Override
    public void delete(Long id) {
        elementRepository.delete(getAndCheckElement(id));
        log.info("delete: {} с id = {}", elementName, id);
    }

    @Override
    public void deleteAll() {
        elementRepository.deleteAll();
        log.info("deleteAll: " + elementName);
    }
}

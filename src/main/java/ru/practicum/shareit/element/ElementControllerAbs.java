package ru.practicum.shareit.element;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.shareit.element.service.ElementService;

public abstract class ElementControllerAbs<T> {
    private final ElementService<T> elementService;

    protected ElementControllerAbs(ElementService<T> elementService) {
        this.elementService = elementService;
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        elementService.delete(id);
    }

    @DeleteMapping
    public void deleteAll() {
        elementService.deleteAll();
    }
}

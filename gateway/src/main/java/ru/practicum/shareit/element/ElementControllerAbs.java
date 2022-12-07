package ru.practicum.shareit.element;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.shareit.element.client.ElementClientAbs;

public abstract class ElementControllerAbs {
    private final ElementClientAbs elementClient;

    protected ElementControllerAbs(ElementClientAbs elementClient) {
        this.elementClient = elementClient;
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        elementClient.delete(id);
    }

    @DeleteMapping
    public void deleteAll() {
        elementClient.deleteAll();
    }
}

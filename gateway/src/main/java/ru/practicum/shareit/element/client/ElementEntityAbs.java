package ru.practicum.shareit.element.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

public abstract class ElementEntityAbs<D> {
    protected HttpHeaders getHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }

        return headers;
    }

    public HttpEntity<D> getEntity(D body, Long userId) {
        return new HttpEntity<>(body, getHeaders(userId));
    }
}

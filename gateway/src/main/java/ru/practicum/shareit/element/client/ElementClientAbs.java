package ru.practicum.shareit.element.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

public abstract class ElementClientAbs {
    protected final RestTemplate rest;

    protected ElementClientAbs(RestTemplateBuilder builder, String serverUrl, String apiPrefix) {
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + apiPrefix))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public void delete(Long id) {
        rest.delete("/" + id);
    }

    public void deleteAll() {
        rest.delete("");
    }
}

package ru.practicum.shareit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.element.model.ElementDtoMapperAbs;
import ru.practicum.shareit.element.model.Identifiable;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

public abstract class ElementTestUtils<E extends Identifiable, D extends Identifiable> {
    public final ElementDtoMapperAbs<E, D> elementDtoMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    public ElementTestUtils(ElementDtoMapperAbs<E, D> elementDtoMapper) {
        this.elementDtoMapper = elementDtoMapper;
    }

    private HttpHeaders getSharerHeader(Long userId) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (userId != null) {
            httpHeaders.put("X-Sharer-User-Id", Collections.singletonList(String.valueOf(userId)));
        }

        return httpHeaders;
    }

    public D toDto(MvcResult mvcResult) throws Exception {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                elementDtoMapper.getDtoClass());
    }

    public List<D> toDtoList(MvcResult mvcResult) throws Exception {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                elementDtoMapper.getDtoListType());
    }

    protected void enrichElement(E element, D elementDto) {
        element.setId(elementDto.getId());
    }

    public MvcResult performPost(String path, Long userId, E element, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(
                        MockMvcRequestBuilders.post(path)
                                .headers(getSharerHeader(userId))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(elementDtoMapper.toDto(element)))
                )
                .andExpect(expectedStatus)
                .andReturn();
    }

    public D performPostDto(String path, Long userId, E element, ResultMatcher expectedStatus) throws Exception {
        D elementDto = toDto(performPost(path, userId, element, expectedStatus));
        enrichElement(element, elementDto);
        return elementDto;
    }

    public MvcResult performGet(String path, Long userId, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(
                        MockMvcRequestBuilders.get(path)
                                .headers(getSharerHeader(userId))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(expectedStatus)
                .andReturn();
    }

    public D performGetDto(String path, Long userId, ResultMatcher expectedStatus) throws Exception {
        return toDto(performGet(path, userId, expectedStatus));
    }

    public List<D> performGetDtoList(String path, Long userId, ResultMatcher expectedStatus) throws Exception {
        return toDtoList(performGet(path, userId, expectedStatus));
    }

    public MvcResult performPatch(String path, Long userId, E element, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(
                        patch(path)
                                .headers(getSharerHeader(userId))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(elementDtoMapper.toDto(element)))
                )
                .andExpect(expectedStatus)
                .andReturn();
    }

    public D performPatchDto(String path, Long userId, E element, ResultMatcher expectedStatus) throws Exception {
        return toDto(performPatch(path, userId, element, expectedStatus));
    }

    public MvcResult performDelete(String path, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(delete(path))
                .andExpect(expectedStatus)
                .andReturn();
    }
}

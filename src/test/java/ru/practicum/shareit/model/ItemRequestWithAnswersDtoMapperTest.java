package ru.practicum.shareit.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.request.model.ItemRequestWithAnswers;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class ItemRequestWithAnswersDtoMapperTest {
    @Autowired
    private TestData td;

    @Test
    void toDto_returnCorrectDto() {
        ItemRequestWithAnswers itemRequestWithAnswers = new ItemRequestWithAnswers(
                td.itemRequestPhone,
                Arrays.asList(td.itemPhoneWithRequest, td.itemPhone4gWithRequest)
        );

        itemRequestWithAnswers.setId(1L);
        td.itemPhoneWithRequest.setId(2L);
        td.itemPhone4gWithRequest.setId(3L);

        ItemRequestWithAnswersDto itemRequestDto = td.itemRequestWithAnswersUtils.elementDtoMapper
                .toDto(itemRequestWithAnswers);

        assertEquals(td.itemRequestPhone.getId(), itemRequestWithAnswers.getId());
        assertEquals(td.itemRequestPhone.getId(), itemRequestDto.getId());
        assertEquals(td.itemRequestPhone.getDescription(), itemRequestDto.getDescription());
        assertEquals(td.itemRequestPhone.getRequestor().getId(), itemRequestDto.getRequestorId());
        assertEquals(td.itemRequestPhone.getCreated(), itemRequestDto.getCreated());
        assertEquals(td.itemPhoneWithRequest.getId(), itemRequestDto.getItems().get(0).getId());
        assertEquals(td.itemPhoneWithRequest.getName(), itemRequestDto.getItems().get(0).getName());
        assertEquals(td.itemPhoneWithRequest.getDescription(), itemRequestDto.getItems().get(0).getDescription());
        assertEquals(td.itemPhoneWithRequest.getAvailable(), itemRequestDto.getItems().get(0).getAvailable());
        assertEquals(td.itemPhoneWithRequest.getOwner().getId(), itemRequestDto.getItems().get(0).getOwnerId());
        assertEquals(td.itemPhoneWithRequest.getRequest().getId(), itemRequestDto.getItems().get(0).getRequestId());
        assertEquals(td.itemPhone4gWithRequest.getId(), itemRequestDto.getItems().get(1).getId());
        assertEquals(td.itemPhone4gWithRequest.getName(), itemRequestDto.getItems().get(1).getName());
        assertEquals(td.itemPhone4gWithRequest.getDescription(), itemRequestDto.getItems().get(1).getDescription());
        assertEquals(td.itemPhone4gWithRequest.getAvailable(), itemRequestDto.getItems().get(1).getAvailable());
        assertEquals(td.itemPhone4gWithRequest.getOwner().getId(), itemRequestDto.getItems().get(1).getOwnerId());
        assertEquals(td.itemPhone4gWithRequest.getRequest().getId(), itemRequestDto.getItems().get(1).getRequestId());
    }
}

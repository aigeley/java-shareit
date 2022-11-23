package ru.practicum.shareit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestWithAnswersDto;
import ru.practicum.shareit.user.UserController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemRequestControllerTest {
    private final TestData td;

    @Autowired
    public ItemRequestControllerTest(TestData td) {
        this.td = td;
    }

    @BeforeEach
    void setUp() throws Exception {
        td
                .addUserOwner()
                .addUserBooker();
    }

    @AfterEach
    void tearDown() throws Exception {
        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userOwner.getId(), status().isOk());
        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userBooker.getId(), status().isOk());
    }

    @Test
    void add_return200AndSameItemRequest() throws Exception {
        td.itemRequestPhone.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        ItemRequestDto itemRequestDto = td.itemRequestUtils.performPostDto(ItemRequestController.BASE_PATH,
                td.userBooker.getId(), td.itemRequestPhone, status().isOk());

        assertEquals(td.itemRequestPhone.getDescription(), itemRequestDto.getDescription());
        assertEquals(td.itemRequestPhone.getRequestor().getId(), itemRequestDto.getRequestorId());
        assertTrue(td.itemRequestPhone.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemRequestDto.getCreated()));
        assertTrue(td.itemRequestPhone.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemRequestDto.getCreated()));
    }

    @Test
    void add_get_return200AndSameItemRequestWithAnswers() throws Exception {
        td
                .addItemRequestPhone()
                .addItemPhoneWithRequest()
                .addItemPhone4gWithRequest();

        ItemRequestWithAnswersDto itemRequestGetDto = td.itemRequestWithAnswersUtils.performGetDto(
                ItemRequestController.BASE_PATH + "/" + td.itemRequestPhone.getId(),
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(td.itemRequestPhone.getId(), itemRequestGetDto.getId());
        assertEquals(td.itemRequestPhone.getDescription(), itemRequestGetDto.getDescription());
        assertEquals(td.itemRequestPhone.getRequestor().getId(), itemRequestGetDto.getRequestorId());
        assertTrue(td.itemRequestPhone.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemRequestGetDto.getCreated()));
        assertTrue(td.itemRequestPhone.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemRequestGetDto.getCreated()));
        assertEquals(td.itemPhoneWithRequest.getId(), itemRequestGetDto.getItems().get(0).getId());
        assertEquals(td.itemPhoneWithRequest.getName(), itemRequestGetDto.getItems().get(0).getName());
        assertEquals(td.itemPhoneWithRequest.getDescription(), itemRequestGetDto.getItems().get(0).getDescription());
        assertEquals(td.itemPhoneWithRequest.getAvailable(), itemRequestGetDto.getItems().get(0).getAvailable());
        assertEquals(td.itemPhoneWithRequest.getOwner().getId(), itemRequestGetDto.getItems().get(0).getOwnerId());
        assertEquals(td.itemPhoneWithRequest.getRequest().getId(), itemRequestGetDto.getItems().get(0).getRequestId());
        assertEquals(td.itemPhone4gWithRequest.getId(), itemRequestGetDto.getItems().get(1).getId());
        assertEquals(td.itemPhone4gWithRequest.getName(), itemRequestGetDto.getItems().get(1).getName());
        assertEquals(td.itemPhone4gWithRequest.getDescription(), itemRequestGetDto.getItems().get(1).getDescription());
        assertEquals(td.itemPhone4gWithRequest.getAvailable(), itemRequestGetDto.getItems().get(1).getAvailable());
        assertEquals(td.itemPhone4gWithRequest.getOwner().getId(), itemRequestGetDto.getItems().get(1).getOwnerId());
        assertEquals(td.itemPhone4gWithRequest.getRequest().getId(),
                itemRequestGetDto.getItems().get(1).getRequestId());
    }

    @Test
    void add_getAllByCurrentUser_return200AndListOfAllItemRequests() throws Exception {
        td
                .addItemRequestPhone()
                .addItemPhoneWithRequest()
                .addItemPhone4gWithRequest()
                .addItemRequestBook();

        List<ItemRequestWithAnswersDto> itemRequestsGetAllDto = td.itemRequestWithAnswersUtils.performGetDtoList(
                ItemRequestController.BASE_PATH,
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(2, itemRequestsGetAllDto.size());
        assertEquals(td.itemRequestBook.getId(),
                itemRequestsGetAllDto.get(0).getId());
        assertEquals(td.itemRequestBook.getDescription(),
                itemRequestsGetAllDto.get(0).getDescription());
        assertEquals(td.itemRequestBook.getRequestor().getId(),
                itemRequestsGetAllDto.get(0).getRequestorId());
        assertTrue(td.itemRequestBook.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemRequestsGetAllDto.get(0).getCreated()));
        assertTrue(td.itemRequestBook.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemRequestsGetAllDto.get(0).getCreated()));
        assertEquals(td.itemRequestPhone.getId(),
                itemRequestsGetAllDto.get(1).getId());
        assertEquals(td.itemRequestPhone.getDescription(),
                itemRequestsGetAllDto.get(1).getDescription());
        assertEquals(td.itemRequestPhone.getRequestor().getId(),
                itemRequestsGetAllDto.get(1).getRequestorId());
        assertTrue(td.itemRequestPhone.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemRequestsGetAllDto.get(1).getCreated()));
        assertTrue(td.itemRequestPhone.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemRequestsGetAllDto.get(1).getCreated()));
        assertEquals(2, itemRequestsGetAllDto.get(1).getItems().size());
        assertEquals(td.itemPhoneWithRequest.getId(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getId());
        assertEquals(td.itemPhoneWithRequest.getName(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getName());
        assertEquals(td.itemPhoneWithRequest.getDescription(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getDescription());
        assertEquals(td.itemPhoneWithRequest.getAvailable(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getAvailable());
        assertEquals(td.itemPhoneWithRequest.getOwner().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getOwnerId());
        assertEquals(td.itemPhoneWithRequest.getRequest().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getRequestId());
        assertEquals(td.itemPhone4gWithRequest.getId(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getId());
        assertEquals(td.itemPhone4gWithRequest.getName(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getName());
        assertEquals(td.itemPhone4gWithRequest.getDescription(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getDescription());
        assertEquals(td.itemPhone4gWithRequest.getAvailable(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getAvailable());
        assertEquals(td.itemPhone4gWithRequest.getOwner().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getOwnerId());
        assertEquals(td.itemPhone4gWithRequest.getRequest().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getRequestId());
    }

    @Test
    void add_getAllByOtherUsers_return200AndListOfAllItemRequests() throws Exception {
        td
                .addItemRequestPhone()
                .addItemPhoneWithRequest()
                .addItemPhone4gWithRequest()
                .addItemRequestBook();

        List<ItemRequestWithAnswersDto> itemRequestsGetAllDto = td.itemRequestWithAnswersUtils.performGetDtoList(
                ItemRequestController.BASE_PATH + "/all?from=0&size=2",
                td.userOwner.getId(),
                status().isOk()
        );

        assertEquals(2, itemRequestsGetAllDto.size());
        assertEquals(td.itemRequestBook.getId(),
                itemRequestsGetAllDto.get(0).getId());
        assertEquals(td.itemRequestBook.getDescription(),
                itemRequestsGetAllDto.get(0).getDescription());
        assertEquals(td.itemRequestBook.getRequestor().getId(),
                itemRequestsGetAllDto.get(0).getRequestorId());
        assertTrue(td.itemRequestBook.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemRequestsGetAllDto.get(0).getCreated()));
        assertTrue(td.itemRequestBook.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemRequestsGetAllDto.get(0).getCreated()));
        assertEquals(td.itemRequestPhone.getId(),
                itemRequestsGetAllDto.get(1).getId());
        assertEquals(td.itemRequestPhone.getDescription(),
                itemRequestsGetAllDto.get(1).getDescription());
        assertEquals(td.itemRequestPhone.getRequestor().getId(),
                itemRequestsGetAllDto.get(1).getRequestorId());
        assertTrue(td.itemRequestPhone.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemRequestsGetAllDto.get(1).getCreated()));
        assertTrue(td.itemRequestPhone.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemRequestsGetAllDto.get(1).getCreated()));
        assertEquals(2, itemRequestsGetAllDto.get(1).getItems().size());
        assertEquals(td.itemPhoneWithRequest.getId(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getId());
        assertEquals(td.itemPhoneWithRequest.getName(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getName());
        assertEquals(td.itemPhoneWithRequest.getDescription(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getDescription());
        assertEquals(td.itemPhoneWithRequest.getAvailable(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getAvailable());
        assertEquals(td.itemPhoneWithRequest.getOwner().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getOwnerId());
        assertEquals(td.itemPhoneWithRequest.getRequest().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(0).getRequestId());
        assertEquals(td.itemPhone4gWithRequest.getId(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getId());
        assertEquals(td.itemPhone4gWithRequest.getName(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getName());
        assertEquals(td.itemPhone4gWithRequest.getDescription(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getDescription());
        assertEquals(td.itemPhone4gWithRequest.getAvailable(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getAvailable());
        assertEquals(td.itemPhone4gWithRequest.getOwner().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getOwnerId());
        assertEquals(td.itemPhone4gWithRequest.getRequest().getId(),
                itemRequestsGetAllDto.get(1).getItems().get(1).getRequestId());
    }

    @Test
    void add_getAllByOtherUsers_firstPage_return200AndFirstItemRequest() throws Exception {
        td
                .addItemRequestPhone()
                .addItemRequestBook();

        List<ItemRequestWithAnswersDto> itemRequestsGetAllDto = td.itemRequestWithAnswersUtils.performGetDtoList(
                ItemRequestController.BASE_PATH + "/all?from=0&size=1",
                td.userOwner.getId(),
                status().isOk()
        );

        assertEquals(1, itemRequestsGetAllDto.size());
        assertEquals(td.itemRequestBook.getId(),
                itemRequestsGetAllDto.get(0).getId());
        assertEquals(td.itemRequestBook.getDescription(),
                itemRequestsGetAllDto.get(0).getDescription());
        assertEquals(td.itemRequestBook.getRequestor().getId(),
                itemRequestsGetAllDto.get(0).getRequestorId());
        assertTrue(td.itemRequestBook.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemRequestsGetAllDto.get(0).getCreated()));
        assertTrue(td.itemRequestBook.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemRequestsGetAllDto.get(0).getCreated()));
    }

    @Test
    void add_getAllByOtherUsers_emptyPage_return200AndEmptyList() throws Exception {
        td
                .addItemRequestPhone()
                .addItemRequestBook();

        List<ItemRequestWithAnswersDto> itemRequestsGetAllDto = td.itemRequestWithAnswersUtils.performGetDtoList(
                ItemRequestController.BASE_PATH + "/all",
                td.userOwner.getId(),
                status().isOk()
        );

        assertEquals(0, itemRequestsGetAllDto.size());
    }
}
package ru.practicum.shareit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;
import ru.practicum.shareit.user.UserController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
    @Autowired
    private TestData td;

    @BeforeEach
    void setUp() throws Exception {
        td
                .addUserOwner()
                .addUserBooker()
                .addItemRequestPhone()
                .addItemRequestBook();
    }

    @AfterEach
    void tearDown() throws Exception {
        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userOwner.getId(), status().isOk());
        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userBooker.getId(), status().isOk());
    }

    @Test
    void add_return200AndSameItem() throws Exception {
        ItemDto itemDto = td.itemUtils.performPostDto(ItemController.BASE_PATH,
                td.userOwner.getId(), td.itemPhoneWithRequest, status().isOk());

        assertEquals(td.itemPhoneWithRequest.getName(), itemDto.getName());
        assertEquals(td.itemPhoneWithRequest.getDescription(), itemDto.getDescription());
        assertEquals(td.itemPhoneWithRequest.getAvailable(), itemDto.getAvailable());
        assertEquals(td.itemPhoneWithRequest.getOwner().getId(), itemDto.getOwnerId());
        assertEquals(td.itemPhoneWithRequest.getRequest().getId(), itemDto.getRequestId());
    }

    @Test
    void add_update_return200AndUpdatedItem() throws Exception {
        td.addItemUnavailable();

        ItemDto itemUpdateDto = td.itemUtils.performPatchDto(ItemController.BASE_PATH + "/"
                        + td.itemUnavailable.getId(),
                td.userOwner.getId(), td.itemPhoneWithRequest, status().isOk());

        assertEquals(td.itemUnavailable.getId(), itemUpdateDto.getId());
        assertEquals(td.itemPhoneWithRequest.getName(), itemUpdateDto.getName());
        assertEquals(td.itemPhoneWithRequest.getDescription(), itemUpdateDto.getDescription());
        assertEquals(td.itemPhoneWithRequest.getAvailable(), itemUpdateDto.getAvailable());
        assertEquals(td.itemUnavailable.getOwner().getId(), itemUpdateDto.getOwnerId());
        assertNull(itemUpdateDto.getRequestId());
    }

    @Test
    void add_update_byBooker_return404() throws Exception {
        td.addItemUnavailable();

        td.itemUtils.performPatch(ItemController.BASE_PATH + "/" + td.itemUnavailable.getId(),
                td.userBooker.getId(), td.itemPhoneWithRequest, status().isNotFound());
    }

    @Test
    void add_get_itemWithBookingsByOwner_return200AndSameItemWithBookingsAndComments() throws Exception {
        td
                .addItemWithBookings()
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected()
                .addCommentText()
                .addCommentEmoji();

        ItemWithBookingsDto itemGetDto = td.itemWithBookingsUtils.performGetDto(ItemController.BASE_PATH + "/"
                        + td.itemWithBookings.getId(),
                td.userOwner.getId(), status().isOk());

        assertEquals(td.itemWithBookings.getId(), itemGetDto.getId());
        assertEquals(td.itemWithBookings.getName(), itemGetDto.getName());
        assertEquals(td.itemWithBookings.getDescription(), itemGetDto.getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), itemGetDto.getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), itemGetDto.getOwnerId());
        assertNull(itemGetDto.getRequestId());
        assertEquals(td.bookingPastApproved.getId(), itemGetDto.getLastBooking().getId());
        assertEquals(td.bookingPastApproved.getStart(), itemGetDto.getLastBooking().getStart());
        assertEquals(td.bookingPastApproved.getEnd(), itemGetDto.getLastBooking().getEnd());
        assertEquals(td.bookingPastApproved.getItem().getId(), itemGetDto.getLastBooking().getItemId());
        assertEquals(td.bookingPastApproved.getBooker().getId(), itemGetDto.getLastBooking().getBookerId());
        assertEquals(td.bookingPastApproved.getStatus(), itemGetDto.getLastBooking().getStatus());
        assertEquals(td.bookingFutureWaiting.getId(), itemGetDto.getNextBooking().getId());
        assertEquals(td.bookingFutureWaiting.getStart(), itemGetDto.getNextBooking().getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), itemGetDto.getNextBooking().getEnd());
        assertEquals(td.bookingFutureWaiting.getItem().getId(), itemGetDto.getNextBooking().getItemId());
        assertEquals(td.bookingFutureWaiting.getBooker().getId(), itemGetDto.getNextBooking().getBookerId());
        assertEquals(td.bookingFutureWaiting.getStatus(), itemGetDto.getNextBooking().getStatus());
        assertEquals(2, itemGetDto.getComments().size());
        assertEquals(td.commentText.getId(), itemGetDto.getComments().get(0).getId());
        assertEquals(td.commentText.getText(), itemGetDto.getComments().get(0).getText());
        assertEquals(td.commentText.getItem().getId(), itemGetDto.getComments().get(0).getItemId());
        assertEquals(td.commentText.getAuthor().getName(), itemGetDto.getComments().get(0).getAuthorName());
        assertTrue(td.commentText.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemGetDto.getComments().get(0).getCreated()));
        assertTrue(td.commentText.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemGetDto.getComments().get(0).getCreated()));
        assertEquals(td.commentEmoji.getId(), itemGetDto.getComments().get(1).getId());
        assertEquals(td.commentEmoji.getText(), itemGetDto.getComments().get(1).getText());
        assertEquals(td.commentEmoji.getItem().getId(), itemGetDto.getComments().get(1).getItemId());
        assertEquals(td.commentEmoji.getAuthor().getName(), itemGetDto.getComments().get(1).getAuthorName());
        assertTrue(td.commentEmoji.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemGetDto.getComments().get(1).getCreated()));
        assertTrue(td.commentEmoji.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemGetDto.getComments().get(1).getCreated()));
    }

    @Test
    void add_get_itemWithBookingsByBooker_return200AndSameItemWithComments() throws Exception {
        td
                .addItemWithBookings()
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected()
                .addCommentText()
                .addCommentEmoji();

        ItemWithBookingsDto itemGetDto = td.itemWithBookingsUtils.performGetDto(ItemController.BASE_PATH + "/"
                        + td.itemWithBookings.getId(),
                td.userBooker.getId(), status().isOk());

        assertEquals(td.itemWithBookings.getId(), itemGetDto.getId());
        assertEquals(td.itemWithBookings.getName(), itemGetDto.getName());
        assertEquals(td.itemWithBookings.getDescription(), itemGetDto.getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), itemGetDto.getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), itemGetDto.getOwnerId());
        assertNull(itemGetDto.getRequestId());
        assertNull(itemGetDto.getLastBooking());
        assertNull(itemGetDto.getNextBooking());
        assertEquals(2, itemGetDto.getComments().size());
        assertEquals(td.commentText.getId(), itemGetDto.getComments().get(0).getId());
        assertEquals(td.commentText.getText(), itemGetDto.getComments().get(0).getText());
        assertEquals(td.commentText.getItem().getId(), itemGetDto.getComments().get(0).getItemId());
        assertEquals(td.commentText.getAuthor().getName(), itemGetDto.getComments().get(0).getAuthorName());
        assertTrue(td.commentText.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemGetDto.getComments().get(0).getCreated()));
        assertTrue(td.commentText.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemGetDto.getComments().get(0).getCreated()));
        assertEquals(td.commentEmoji.getId(), itemGetDto.getComments().get(1).getId());
        assertEquals(td.commentEmoji.getText(), itemGetDto.getComments().get(1).getText());
        assertEquals(td.commentEmoji.getItem().getId(), itemGetDto.getComments().get(1).getItemId());
        assertEquals(td.commentEmoji.getAuthor().getName(), itemGetDto.getComments().get(1).getAuthorName());
        assertTrue(td.commentEmoji.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemGetDto.getComments().get(1).getCreated()));
        assertTrue(td.commentEmoji.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemGetDto.getComments().get(1).getCreated()));
    }

    @Test
    void add_getAll_return200AndListOfAllItems() throws Exception {
        td
                .addItemUnavailable()
                .addItemPhoneWithRequest()
                .addItemWithBookings()
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected()
                .addCommentText()
                .addCommentEmoji();

        List<ItemWithBookingsDto> itemsGetAllDto = td.itemWithBookingsUtils.performGetDtoList(ItemController.BASE_PATH
                        + "?from=0&size=20",
                td.userOwner.getId(), status().isOk());

        assertEquals(3, itemsGetAllDto.size());
        assertEquals(td.itemUnavailable.getId(), itemsGetAllDto.get(0).getId());
        assertEquals(td.itemUnavailable.getName(), itemsGetAllDto.get(0).getName());
        assertEquals(td.itemUnavailable.getDescription(), itemsGetAllDto.get(0).getDescription());
        assertEquals(td.itemUnavailable.getAvailable(), itemsGetAllDto.get(0).getAvailable());
        assertEquals(td.itemUnavailable.getOwner().getId(), itemsGetAllDto.get(0).getOwnerId());
        assertNull(itemsGetAllDto.get(0).getRequestId());
        assertEquals(td.itemPhoneWithRequest.getId(), itemsGetAllDto.get(1).getId());
        assertEquals(td.itemPhoneWithRequest.getName(), itemsGetAllDto.get(1).getName());
        assertEquals(td.itemPhoneWithRequest.getDescription(), itemsGetAllDto.get(1).getDescription());
        assertEquals(td.itemPhoneWithRequest.getAvailable(), itemsGetAllDto.get(1).getAvailable());
        assertEquals(td.itemPhoneWithRequest.getOwner().getId(), itemsGetAllDto.get(1).getOwnerId());
        assertEquals(td.itemPhoneWithRequest.getRequest().getId(), itemsGetAllDto.get(1).getRequestId());
        assertEquals(td.itemWithBookings.getId(), itemsGetAllDto.get(2).getId());
        assertEquals(td.itemWithBookings.getName(), itemsGetAllDto.get(2).getName());
        assertEquals(td.itemWithBookings.getDescription(), itemsGetAllDto.get(2).getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), itemsGetAllDto.get(2).getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), itemsGetAllDto.get(2).getOwnerId());
        assertNull(itemsGetAllDto.get(2).getRequestId());
        assertEquals(td.bookingPastApproved.getId(), itemsGetAllDto.get(2).getLastBooking().getId());
        assertEquals(td.bookingPastApproved.getStart(), itemsGetAllDto.get(2).getLastBooking().getStart());
        assertEquals(td.bookingPastApproved.getEnd(), itemsGetAllDto.get(2).getLastBooking().getEnd());
        assertEquals(td.bookingPastApproved.getItem().getId(), itemsGetAllDto.get(2).getLastBooking().getItemId());
        assertEquals(td.bookingPastApproved.getBooker().getId(), itemsGetAllDto.get(2).getLastBooking().getBookerId());
        assertEquals(td.bookingPastApproved.getStatus(), itemsGetAllDto.get(2).getLastBooking().getStatus());
        assertEquals(td.bookingFutureWaiting.getId(), itemsGetAllDto.get(2).getNextBooking().getId());
        assertEquals(td.bookingFutureWaiting.getStart(), itemsGetAllDto.get(2).getNextBooking().getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), itemsGetAllDto.get(2).getNextBooking().getEnd());
        assertEquals(td.bookingFutureWaiting.getItem().getId(), itemsGetAllDto.get(2).getNextBooking().getItemId());
        assertEquals(td.bookingFutureWaiting.getBooker().getId(), itemsGetAllDto.get(2).getNextBooking().getBookerId());
        assertEquals(td.bookingFutureWaiting.getStatus(), itemsGetAllDto.get(2).getNextBooking().getStatus());
        assertEquals(2, itemsGetAllDto.get(2).getComments().size());
        assertEquals(td.commentText.getId(), itemsGetAllDto.get(2).getComments().get(0).getId());
        assertEquals(td.commentText.getText(), itemsGetAllDto.get(2).getComments().get(0).getText());
        assertEquals(td.commentText.getItem().getId(), itemsGetAllDto.get(2).getComments().get(0).getItemId());
        assertEquals(td.commentText.getAuthor().getName(), itemsGetAllDto.get(2).getComments().get(0).getAuthorName());
        assertTrue(td.commentText.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemsGetAllDto.get(2).getComments().get(0).getCreated()));
        assertTrue(td.commentText.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemsGetAllDto.get(2).getComments().get(0).getCreated()));
        assertEquals(td.commentEmoji.getId(), itemsGetAllDto.get(2).getComments().get(1).getId());
        assertEquals(td.commentEmoji.getText(), itemsGetAllDto.get(2).getComments().get(1).getText());
        assertEquals(td.commentEmoji.getItem().getId(), itemsGetAllDto.get(2).getComments().get(1).getItemId());
        assertEquals(td.commentEmoji.getAuthor().getName(), itemsGetAllDto.get(2).getComments().get(1).getAuthorName());
        assertTrue(td.commentEmoji.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(itemsGetAllDto.get(2).getComments().get(1).getCreated()));
        assertTrue(td.commentEmoji.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(itemsGetAllDto.get(2).getComments().get(1).getCreated()));
    }

    @Test
    void add_search_return200AndListOfAvailableItems() throws Exception {
        td
                .addItemUnavailable()
                .addItemPhoneWithRequest()
                .addItemWithBookings();

        List<ItemDto> itemsSearchDto = td.itemUtils.performGetDtoList(ItemController.BASE_PATH
                        + "/search?text=spOOn&from=0&size=20",
                td.userBooker.getId(), status().isOk());

        assertEquals(1, itemsSearchDto.size());
        assertEquals(td.itemWithBookings.getId(), itemsSearchDto.get(0).getId());
        assertEquals(td.itemWithBookings.getName(), itemsSearchDto.get(0).getName());
        assertEquals(td.itemWithBookings.getDescription(), itemsSearchDto.get(0).getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), itemsSearchDto.get(0).getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), itemsSearchDto.get(0).getOwnerId());
        assertNull(itemsSearchDto.get(0).getRequestId());
    }

    @Test
    void addComment_return200AndSameComment() throws Exception {
        td
                .addItemWithBookings()
                .addBookingPastApproved();

        td.commentText.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        CommentDto commentDto = td.commentUtils.performPostDto(ItemController.BASE_PATH + "/"
                        + td.itemWithBookings.getId() + "/comment",
                td.userBooker.getId(), td.commentText, status().isOk());

        assertEquals(td.commentText.getText(), commentDto.getText());
        assertEquals(td.commentText.getItem().getId(), commentDto.getItemId());
        assertEquals(td.commentText.getAuthor().getName(), commentDto.getAuthorName());
        assertTrue(td.commentText.getCreated().minusNanos(TestData.START_NANOS)
                .isBefore(commentDto.getCreated()));
        assertTrue(td.commentText.getCreated().plusNanos(TestData.START_NANOS)
                .isAfter(commentDto.getCreated()));
    }

    @Test
    void addComment_byOwner_return400() throws Exception {
        td.addItemWithBookings();

        td.commentUtils.performPost(ItemController.BASE_PATH + "/" + td.itemWithBookings.getId() + "/comment",
                td.userOwner.getId(), td.commentText, status().isBadRequest());
    }
}
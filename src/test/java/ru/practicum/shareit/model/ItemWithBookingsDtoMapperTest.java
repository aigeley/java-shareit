package ru.practicum.shareit.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.item.model.ItemWithBookings;
import ru.practicum.shareit.item.model.ItemWithBookingsDto;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc
class ItemWithBookingsDtoMapperTest {
    private final TestData td;

    @Autowired
    public ItemWithBookingsDtoMapperTest(TestData td) {
        this.td = td;
    }

    @Test
    void toDto_returnCorrectDto() {
        ItemWithBookings itemWithBookings = new ItemWithBookings(
                td.itemWithBookings,
                td.bookingPastApproved,
                td.bookingFutureWaiting,
                Arrays.asList(td.commentText, td.commentEmoji)
        );

        itemWithBookings.setId(1L);
        td.bookingPastApproved.setId(2L);
        td.bookingFutureWaiting.setId(3L);
        td.commentText.setId(4L);
        td.commentEmoji.setId(5L);

        ItemWithBookingsDto itemDto = td.itemWithBookingsUtils.elementDtoMapper.toDto(itemWithBookings);

        assertEquals(td.itemWithBookings.getId(), itemWithBookings.getId());
        assertEquals(td.itemWithBookings.getId(), itemDto.getId());
        assertEquals(td.itemWithBookings.getName(), itemDto.getName());
        assertEquals(td.itemWithBookings.getDescription(), itemDto.getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), itemDto.getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), itemDto.getOwnerId());
        assertNull(itemDto.getRequestId());
        assertEquals(td.bookingPastApproved.getId(), itemDto.getLastBooking().getId());
        assertEquals(td.bookingPastApproved.getStart(), itemDto.getLastBooking().getStart());
        assertEquals(td.bookingPastApproved.getEnd(), itemDto.getLastBooking().getEnd());
        assertEquals(td.bookingPastApproved.getItem().getId(), itemDto.getLastBooking().getItemId());
        assertEquals(td.bookingPastApproved.getBooker().getId(), itemDto.getLastBooking().getBookerId());
        assertEquals(td.bookingPastApproved.getStatus(), itemDto.getLastBooking().getStatus());
        assertEquals(td.bookingFutureWaiting.getId(), itemDto.getNextBooking().getId());
        assertEquals(td.bookingFutureWaiting.getStart(), itemDto.getNextBooking().getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), itemDto.getNextBooking().getEnd());
        assertEquals(td.bookingFutureWaiting.getItem().getId(), itemDto.getNextBooking().getItemId());
        assertEquals(td.bookingFutureWaiting.getBooker().getId(), itemDto.getNextBooking().getBookerId());
        assertEquals(td.bookingFutureWaiting.getStatus(), itemDto.getNextBooking().getStatus());
        assertEquals(2, itemDto.getComments().size());
        assertEquals(td.commentText.getId(), itemDto.getComments().get(0).getId());
        assertEquals(td.commentText.getText(), itemDto.getComments().get(0).getText());
        assertEquals(td.commentText.getItem().getId(), itemDto.getComments().get(0).getItemId());
        assertEquals(td.commentText.getAuthor().getName(), itemDto.getComments().get(0).getAuthorName());
        assertEquals(td.commentText.getCreated(), itemDto.getComments().get(0).getCreated());
        assertEquals(td.commentEmoji.getId(), itemDto.getComments().get(1).getId());
        assertEquals(td.commentEmoji.getText(), itemDto.getComments().get(1).getText());
        assertEquals(td.commentEmoji.getItem().getId(), itemDto.getComments().get(1).getItemId());
        assertEquals(td.commentEmoji.getAuthor().getName(), itemDto.getComments().get(1).getAuthorName());
        assertEquals(td.commentEmoji.getCreated(), itemDto.getComments().get(1).getCreated());
    }
}

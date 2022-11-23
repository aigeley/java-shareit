package ru.practicum.shareit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.model.BookingWithEntitiesDto;
import ru.practicum.shareit.user.UserController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {
    private final TestData td;

    @Autowired
    public BookingControllerTest(TestData td) {
        this.td = td;
    }

    @BeforeEach
    void setUp() throws Exception {
        td
                .addUserOwner()
                .addUserBooker()
                .addItemWithBookings();
    }

    @AfterEach
    void tearDown() throws Exception {
        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userOwner.getId(), status().isOk());
        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userBooker.getId(), status().isOk());
    }

    @Test
    void add_return200AndSameBooking() throws Exception {
        BookingWithEntitiesDto bookingDto = td.bookingWithEntitiesUtils.toDto(
                td.bookingUtils.performPost(
                        BookingController.BASE_PATH,
                        td.userBooker.getId(),
                        td.bookingFutureWaiting,
                        status().isOk()
                )
        );

        assertEquals(td.bookingFutureWaiting.getStart(), bookingDto.getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), bookingDto.getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingDto.getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingDto.getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingDto.getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingDto.getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingDto.getItem().getOwnerId());
        assertNull(bookingDto.getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingDto.getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingDto.getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingDto.getBooker().getName());
        assertEquals(td.bookingFutureWaiting.getStatus(), bookingDto.getStatus());
    }

    @Test
    void add_itemIsUnavailable_return400() throws Exception {
        td.addItemUnavailable();
        td.bookingFutureWaiting.setItem(td.itemUnavailable);

        td.bookingUtils.performPost(BookingController.BASE_PATH,
                td.userBooker.getId(), td.bookingFutureWaiting, status().isBadRequest());
    }

    @Test
    void add_bookerSameAsOwner_return404() throws Exception {
        td.bookingUtils.performPost(BookingController.BASE_PATH,
                td.userOwner.getId(), td.bookingFutureWaiting, status().isNotFound());
    }

    @Test
    void add_get_return200AndSameBooking() throws Exception {
        td.addBookingFutureWaiting();

        BookingWithEntitiesDto bookingDto = td.bookingWithEntitiesUtils.performGetDto(
                BookingController.BASE_PATH + "/" + td.bookingFutureWaiting.getId(),
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(td.bookingFutureWaiting.getId(), bookingDto.getId());
        assertEquals(td.bookingFutureWaiting.getStart(), bookingDto.getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), bookingDto.getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingDto.getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingDto.getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingDto.getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingDto.getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingDto.getItem().getOwnerId());
        assertNull(bookingDto.getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingDto.getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingDto.getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingDto.getBooker().getName());
        assertEquals(td.bookingFutureWaiting.getStatus(), bookingDto.getStatus());
    }

    @Test
    void add_get_userIsDifferent_return404() throws Exception {
        td
                .addUserOther()
                .addBookingFutureWaiting();

        td.bookingWithEntitiesUtils.performGet(
                BookingController.BASE_PATH + "/" + td.bookingFutureWaiting.getId(),
                td.userOther.getId(),
                status().isNotFound()
        );

        td.userUtils.performDelete(UserController.BASE_PATH + "/" + td.userOther.getId(), status().isOk());
    }

    @Test
    void add_approve_true_return200AndApprovedBooking() throws Exception {
        td.addBookingPastApproved();

        BookingWithEntitiesDto bookingDto = td.bookingWithEntitiesUtils.performPatchDto(BookingController.BASE_PATH
                        + "/" + td.bookingPastApproved.getId() + "?approved=true",
                td.userOwner.getId(), null, status().isOk()
        );

        assertEquals(td.bookingPastApproved.getId(), bookingDto.getId());
        assertEquals(td.bookingPastApproved.getStart(), bookingDto.getStart());
        assertEquals(td.bookingPastApproved.getEnd(), bookingDto.getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingDto.getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingDto.getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingDto.getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingDto.getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingDto.getItem().getOwnerId());
        assertNull(bookingDto.getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingDto.getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingDto.getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingDto.getBooker().getName());
        assertEquals(td.bookingPastApproved.getStatus(), bookingDto.getStatus());
    }

    @Test
    void add_approve_alreadyApproved_return400() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved();

        td.bookingWithEntitiesUtils.performPatch(BookingController.BASE_PATH
                        + "/" + td.bookingPastApproved.getId() + "?approved=true",
                td.userOwner.getId(), null, status().isBadRequest()
        );
    }

    @Test
    void add_approve_false_return200AndRejectedBooking() throws Exception {
        td.addBookingFutureRejected();

        BookingWithEntitiesDto bookingDto = td.bookingWithEntitiesUtils.performPatchDto(BookingController.BASE_PATH
                        + "/" + td.bookingFutureRejected.getId() + "?approved=false",
                td.userOwner.getId(), null, status().isOk()
        );

        assertEquals(td.bookingFutureRejected.getId(), bookingDto.getId());
        assertEquals(td.bookingFutureRejected.getStart(), bookingDto.getStart());
        assertEquals(td.bookingFutureRejected.getEnd(), bookingDto.getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingDto.getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingDto.getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingDto.getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingDto.getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingDto.getItem().getOwnerId());
        assertNull(bookingDto.getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingDto.getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingDto.getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingDto.getBooker().getName());
        assertEquals(td.bookingFutureRejected.getStatus(), bookingDto.getStatus());
    }

    @Test
    void add_getAllByOwner_all_return200AndListOfAllBookings() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected();

        List<BookingWithEntitiesDto> bookingsGetAllDto = td.bookingWithEntitiesUtils.performGetDtoList(
                BookingController.BASE_PATH + "/owner",
                td.userOwner.getId(),
                status().isOk()
        );

        assertEquals(4, bookingsGetAllDto.size());
        assertEquals(td.bookingFutureRejected.getId(), bookingsGetAllDto.get(0).getId());
        assertEquals(td.bookingFutureRejected.getStart(), bookingsGetAllDto.get(0).getStart());
        assertEquals(td.bookingFutureRejected.getEnd(), bookingsGetAllDto.get(0).getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingsGetAllDto.get(0).getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingsGetAllDto.get(0).getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingsGetAllDto.get(0).getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingsGetAllDto.get(0).getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingsGetAllDto.get(0).getItem().getOwnerId());
        assertNull(bookingsGetAllDto.get(0).getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingsGetAllDto.get(0).getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingsGetAllDto.get(0).getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingsGetAllDto.get(0).getBooker().getName());
        assertEquals(td.bookingFutureRejected.getStatus(), bookingsGetAllDto.get(0).getStatus());
        assertEquals(td.bookingFutureWaiting.getId(), bookingsGetAllDto.get(1).getId());
        assertEquals(td.bookingFutureWaiting.getStart(), bookingsGetAllDto.get(1).getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), bookingsGetAllDto.get(1).getEnd());
        assertEquals(td.bookingFutureWaiting.getStatus(), bookingsGetAllDto.get(1).getStatus());
        assertEquals(td.bookingCurrentApproved.getId(), bookingsGetAllDto.get(2).getId());
        assertEquals(td.bookingCurrentApproved.getStart(), bookingsGetAllDto.get(2).getStart());
        assertEquals(td.bookingCurrentApproved.getEnd(), bookingsGetAllDto.get(2).getEnd());
        assertEquals(td.bookingCurrentApproved.getStatus(), bookingsGetAllDto.get(2).getStatus());
        assertEquals(td.bookingPastApproved.getId(), bookingsGetAllDto.get(3).getId());
        assertEquals(td.bookingPastApproved.getStart(), bookingsGetAllDto.get(3).getStart());
        assertEquals(td.bookingPastApproved.getEnd(), bookingsGetAllDto.get(3).getEnd());
        assertEquals(td.bookingPastApproved.getStatus(), bookingsGetAllDto.get(3).getStatus());
    }

    @Test
    void add_getAllByOwner_firstPage_return200AndLastBooking() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected();

        List<BookingWithEntitiesDto> bookingsGetAllDto = td.bookingWithEntitiesUtils.performGetDtoList(
                BookingController.BASE_PATH + "/owner?from=0&size=1",
                td.userOwner.getId(),
                status().isOk()
        );

        assertEquals(1, bookingsGetAllDto.size());
        assertEquals(td.bookingFutureRejected.getId(), bookingsGetAllDto.get(0).getId());
        assertEquals(td.bookingFutureRejected.getStart(), bookingsGetAllDto.get(0).getStart());
        assertEquals(td.bookingFutureRejected.getEnd(), bookingsGetAllDto.get(0).getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingsGetAllDto.get(0).getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingsGetAllDto.get(0).getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingsGetAllDto.get(0).getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingsGetAllDto.get(0).getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingsGetAllDto.get(0).getItem().getOwnerId());
        assertNull(bookingsGetAllDto.get(0).getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingsGetAllDto.get(0).getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingsGetAllDto.get(0).getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingsGetAllDto.get(0).getBooker().getName());
        assertEquals(td.bookingFutureRejected.getStatus(), bookingsGetAllDto.get(0).getStatus());
    }

    @Test
    void add_getAllByBooker_current_return200AndListOfAllBookings() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected();

        List<BookingWithEntitiesDto> bookingsGetAllDto = td.bookingWithEntitiesUtils.performGetDtoList(
                BookingController.BASE_PATH + "?state=current",
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(1, bookingsGetAllDto.size());
        assertEquals(td.bookingCurrentApproved.getId(), bookingsGetAllDto.get(0).getId());
        assertEquals(td.bookingCurrentApproved.getStart(), bookingsGetAllDto.get(0).getStart());
        assertEquals(td.bookingCurrentApproved.getEnd(), bookingsGetAllDto.get(0).getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingsGetAllDto.get(0).getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingsGetAllDto.get(0).getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingsGetAllDto.get(0).getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingsGetAllDto.get(0).getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingsGetAllDto.get(0).getItem().getOwnerId());
        assertNull(bookingsGetAllDto.get(0).getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingsGetAllDto.get(0).getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingsGetAllDto.get(0).getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingsGetAllDto.get(0).getBooker().getName());
        assertEquals(td.bookingCurrentApproved.getStatus(), bookingsGetAllDto.get(0).getStatus());
    }

    @Test
    void add_getAllByBooker_past_return200AndListOfAllBookings() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected();

        List<BookingWithEntitiesDto> bookingsGetAllDto = td.bookingWithEntitiesUtils.performGetDtoList(
                BookingController.BASE_PATH + "?state=past",
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(1, bookingsGetAllDto.size());
        assertEquals(td.bookingPastApproved.getId(), bookingsGetAllDto.get(0).getId());
        assertEquals(td.bookingPastApproved.getStart(), bookingsGetAllDto.get(0).getStart());
        assertEquals(td.bookingPastApproved.getEnd(), bookingsGetAllDto.get(0).getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingsGetAllDto.get(0).getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingsGetAllDto.get(0).getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingsGetAllDto.get(0).getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingsGetAllDto.get(0).getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingsGetAllDto.get(0).getItem().getOwnerId());
        assertNull(bookingsGetAllDto.get(0).getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingsGetAllDto.get(0).getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingsGetAllDto.get(0).getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingsGetAllDto.get(0).getBooker().getName());
        assertEquals(td.bookingPastApproved.getStatus(), bookingsGetAllDto.get(0).getStatus());
    }

    @Test
    void add_getAllByBooker_future_return200AndListOfAllBookings() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected();

        List<BookingWithEntitiesDto> bookingsGetAllDto = td.bookingWithEntitiesUtils.performGetDtoList(
                BookingController.BASE_PATH + "?state=future",
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(2, bookingsGetAllDto.size());
        assertEquals(td.bookingFutureRejected.getId(), bookingsGetAllDto.get(0).getId());
        assertEquals(td.bookingFutureRejected.getStart(), bookingsGetAllDto.get(0).getStart());
        assertEquals(td.bookingFutureRejected.getEnd(), bookingsGetAllDto.get(0).getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingsGetAllDto.get(0).getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingsGetAllDto.get(0).getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingsGetAllDto.get(0).getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingsGetAllDto.get(0).getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingsGetAllDto.get(0).getItem().getOwnerId());
        assertNull(bookingsGetAllDto.get(0).getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingsGetAllDto.get(0).getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingsGetAllDto.get(0).getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingsGetAllDto.get(0).getBooker().getName());
        assertEquals(td.bookingFutureRejected.getStatus(), bookingsGetAllDto.get(0).getStatus());
        assertEquals(td.bookingFutureWaiting.getId(), bookingsGetAllDto.get(1).getId());
        assertEquals(td.bookingFutureWaiting.getStart(), bookingsGetAllDto.get(1).getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), bookingsGetAllDto.get(1).getEnd());
        assertEquals(td.bookingFutureWaiting.getStatus(), bookingsGetAllDto.get(1).getStatus());
    }

    @Test
    void add_getAllByBooker_waiting_return200AndListOfAllBookings() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected();

        List<BookingWithEntitiesDto> bookingsGetAllDto = td.bookingWithEntitiesUtils.performGetDtoList(
                BookingController.BASE_PATH + "?state=waiting",
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(1, bookingsGetAllDto.size());
        assertEquals(td.bookingFutureWaiting.getId(), bookingsGetAllDto.get(0).getId());
        assertEquals(td.bookingFutureWaiting.getStart(), bookingsGetAllDto.get(0).getStart());
        assertEquals(td.bookingFutureWaiting.getEnd(), bookingsGetAllDto.get(0).getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingsGetAllDto.get(0).getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingsGetAllDto.get(0).getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingsGetAllDto.get(0).getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingsGetAllDto.get(0).getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingsGetAllDto.get(0).getItem().getOwnerId());
        assertNull(bookingsGetAllDto.get(0).getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingsGetAllDto.get(0).getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingsGetAllDto.get(0).getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingsGetAllDto.get(0).getBooker().getName());
        assertEquals(td.bookingFutureWaiting.getStatus(), bookingsGetAllDto.get(0).getStatus());
    }

    @Test
    void add_getAllByBooker_rejected_return200AndListOfAllBookings() throws Exception {
        td
                .addBookingPastApproved()
                .approveBookingPastApproved()
                .addBookingCurrentApproved()
                .approveBookingCurrentApproved()
                .addBookingFutureWaiting()
                .addBookingFutureRejected()
                .approveBookingFutureRejected();

        List<BookingWithEntitiesDto> bookingsGetAllDto = td.bookingWithEntitiesUtils.performGetDtoList(
                BookingController.BASE_PATH + "?state=rejected",
                td.userBooker.getId(),
                status().isOk()
        );

        assertEquals(1, bookingsGetAllDto.size());
        assertEquals(td.bookingFutureRejected.getId(), bookingsGetAllDto.get(0).getId());
        assertEquals(td.bookingFutureRejected.getStart(), bookingsGetAllDto.get(0).getStart());
        assertEquals(td.bookingFutureRejected.getEnd(), bookingsGetAllDto.get(0).getEnd());
        assertEquals(td.itemWithBookings.getId(), bookingsGetAllDto.get(0).getItem().getId());
        assertEquals(td.itemWithBookings.getName(), bookingsGetAllDto.get(0).getItem().getName());
        assertEquals(td.itemWithBookings.getDescription(), bookingsGetAllDto.get(0).getItem().getDescription());
        assertEquals(td.itemWithBookings.getAvailable(), bookingsGetAllDto.get(0).getItem().getAvailable());
        assertEquals(td.itemWithBookings.getOwner().getId(), bookingsGetAllDto.get(0).getItem().getOwnerId());
        assertNull(bookingsGetAllDto.get(0).getItem().getRequestId());
        assertEquals(td.userBooker.getId(), bookingsGetAllDto.get(0).getBooker().getId());
        assertEquals(td.userBooker.getEmail(), bookingsGetAllDto.get(0).getBooker().getEmail());
        assertEquals(td.userBooker.getName(), bookingsGetAllDto.get(0).getBooker().getName());
        assertEquals(td.bookingFutureRejected.getStatus(), bookingsGetAllDto.get(0).getStatus());
    }
}
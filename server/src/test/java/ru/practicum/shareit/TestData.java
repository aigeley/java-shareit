package ru.practicum.shareit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.utils.BookingTestUtils;
import ru.practicum.shareit.utils.BookingWithEntitiesTestUtils;
import ru.practicum.shareit.utils.CommentTestUtils;
import ru.practicum.shareit.utils.ItemRequestTestUtils;
import ru.practicum.shareit.utils.ItemRequestWithAnswersTestUtils;
import ru.practicum.shareit.utils.ItemTestUtils;
import ru.practicum.shareit.utils.ItemWithBookingsTestUtils;
import ru.practicum.shareit.utils.UserTestUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Scope("prototype")
public class TestData {
    public static final int NANOS_IN_MILLIS = 1000000;
    public static final int START_MILLIS = 100;
    public static final int START_NANOS = START_MILLIS * NANOS_IN_MILLIS;
    public static final int DURATION_MILLIS = 1;
    public static final int END_MILLIS = START_MILLIS + DURATION_MILLIS;
    public static final int END_NANOS = END_MILLIS * NANOS_IN_MILLIS;
    public static final int WAIT_CURRENT_MILLIS = START_MILLIS + DURATION_MILLIS;
    public static final int WAIT_PAST_MILLIS = END_MILLIS + DURATION_MILLIS;

    @Autowired
    public UserTestUtils userUtils;
    @Autowired
    public ItemRequestTestUtils itemRequestUtils;
    @Autowired
    public ItemRequestWithAnswersTestUtils itemRequestWithAnswersUtils;
    @Autowired
    public ItemTestUtils itemUtils;
    @Autowired
    public ItemWithBookingsTestUtils itemWithBookingsUtils;
    @Autowired
    public CommentTestUtils commentUtils;
    @Autowired
    public BookingTestUtils bookingUtils;
    @Autowired
    public BookingWithEntitiesTestUtils bookingWithEntitiesUtils;

    public final User userOwner;
    public final User userBooker;
    public final User userOther;

    public final ItemRequest itemRequestPhone;
    public final ItemRequest itemRequestBook;

    public final Item itemUnavailable;
    public final Item itemPhoneWithRequest;
    public final Item itemPhone4gWithRequest;
    public final Item itemWithBookings;

    public final Comment commentText;
    public final Comment commentEmoji;

    public final Booking bookingPastApproved;
    public final Booking bookingCurrentApproved;
    public final Booking bookingFutureWaiting;
    public final Booking bookingFutureRejected;

    public TestData() {
        userOwner = new User();
        userOwner.setName("Neo");
        userOwner.setEmail("TAnderson@metacortex.com");
        userBooker = new User();
        userBooker.setName("trinity");
        userBooker.setEmail("392@yandex.ru");
        userOther = new User();
        userOther.setName("Матвей Матвеич");
        userOther.setEmail("kv-1@ya.ru");

        itemRequestPhone = new ItemRequest();
        itemRequestPhone.setDescription("Срочно нужна Nokia 8110 !!1 4g тоже подойдёт)");
        itemRequestPhone.setRequestor(userBooker);
        itemRequestBook = new ItemRequest();
        itemRequestBook.setDescription("Дайте почитать Simulacra & Simulation! @Neo, у тебя вроде была?");
        itemRequestBook.setRequestor(userBooker);

        itemUnavailable = new Item();
        itemUnavailable.setName("Spoon");
        itemUnavailable.setDescription("There is no spoon");
        itemUnavailable.setAvailable(false);
        itemUnavailable.setOwner(userOwner);
        itemUnavailable.setRequest(null);
        itemPhoneWithRequest = new Item();
        itemPhoneWithRequest.setName("Nokia 8110");
        itemPhoneWithRequest.setDescription("Производство: Финляндия\n" +
                "Модель: лето 1996 г.\n" +
                "GSM 900\n" +
                "Низкое энергопотребление\n" +
                "Аккумулятор: Li-Ion 400 mAh без \"эффекта памяти\"\n" +
                "Время работы без подзарядки в режиме разговора до 2ч.5 мин/ в режиме ожидания до 70 ч.\n" +
                "SMS\n" +
                "Графический дисплей до 5 строк.\n" +
                "Русскоязычное меню.\n" +
                "Антенна 2см. не выдвижная\n" +
                "Ответ нажатием любой клавиши или сдвигом крышки.\n" +
                "Габариты 141х48х25 мм\n" +
                "Вес 151 г.");
        itemPhoneWithRequest.setAvailable(true);
        itemPhoneWithRequest.setOwner(userOwner);
        itemPhoneWithRequest.setRequest(itemRequestPhone);
        itemPhone4gWithRequest = new Item();
        itemPhone4gWithRequest.setName("Nokia 8110 4G");
        itemPhone4gWithRequest.setDescription("The return of the icon. Much like the original, the new Nokia 8110 4G " +
                "has a curved protective cover you can slide open to pick up calls and slide back to end them. " +
                "The unique shape also means you can spin the phone in ways you never imagined.");
        itemPhone4gWithRequest.setAvailable(true);
        itemPhone4gWithRequest.setOwner(userOwner);
        itemPhone4gWithRequest.setRequest(itemRequestPhone);
        itemWithBookings = new Item();
        itemWithBookings.setName("Fork");
        itemWithBookings.setDescription("don't fear a fork, but fear a spoon!");
        itemWithBookings.setAvailable(true);
        itemWithBookings.setOwner(userOwner);
        itemWithBookings.setRequest(null);

        commentText = new Comment();
        commentText.setText("one strike and you are on the Moon)");
        commentText.setItem(itemWithBookings);
        commentText.setAuthor(userBooker);
        commentEmoji = new Comment();
        commentEmoji.setText("\uD83C\uDF1A\uD83E\uDD44\uD83D\uDE05");
        commentEmoji.setItem(itemWithBookings);
        commentEmoji.setAuthor(userBooker);

        bookingPastApproved = new Booking();
        bookingPastApproved.setItem(itemWithBookings);
        bookingPastApproved.setBooker(userBooker);
        bookingPastApproved.setStatus(BookingStatus.APPROVED);
        bookingCurrentApproved = new Booking();
        bookingCurrentApproved.setEnd(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).plusDays(1));
        bookingCurrentApproved.setItem(itemWithBookings);
        bookingCurrentApproved.setBooker(userBooker);
        bookingCurrentApproved.setStatus(BookingStatus.APPROVED);
        bookingFutureWaiting = new Booking();
        bookingFutureWaiting.setStart(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).plusDays(1));
        bookingFutureWaiting.setEnd(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).plusDays(2));
        bookingFutureWaiting.setItem(itemWithBookings);
        bookingFutureWaiting.setBooker(userBooker);
        bookingFutureWaiting.setStatus(BookingStatus.WAITING);
        bookingFutureRejected = new Booking();
        bookingFutureRejected.setStart(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).plusDays(2));
        bookingFutureRejected.setEnd(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).plusDays(3));
        bookingFutureRejected.setItem(itemWithBookings);
        bookingFutureRejected.setBooker(userBooker);
        bookingFutureRejected.setStatus(BookingStatus.REJECTED);
    }

    private void addUser(User user) throws Exception {
        userUtils.performPostDto(UserController.BASE_PATH, null, user, status().isOk());
    }

    private void addItemRequest(ItemRequest itemRequest, long userId) throws Exception {
        itemRequestUtils.performPostDto(ItemRequestController.BASE_PATH,
                userId, itemRequest, status().isOk());
    }

    private void addItem(Item item, long userId) throws Exception {
        itemUtils.performPostDto(ItemController.BASE_PATH,
                userId, item, status().isOk());
    }

    private void addBooking(Booking booking, long userId) throws Exception {
        bookingUtils.performPostDto(BookingController.BASE_PATH,
                userId, booking, status().isOk());
    }

    private void approveBooking(long bookingId, boolean isApproved, long userId) throws Exception {
        bookingUtils.performPatchDto(BookingController.BASE_PATH + "/" + bookingId + "?approved=" + isApproved,
                userId, null, status().isOk()
        );
    }

    private void addComment(Comment comment, long itemId, long userId) throws Exception {
        commentUtils.performPostDto(ItemController.BASE_PATH + "/" + itemId + "/comment",
                userId, comment, status().isOk());
    }

    public TestData addUserOwner() throws Exception {
        addUser(userOwner);
        return this;
    }

    public TestData addUserBooker() throws Exception {
        addUser(userBooker);
        return this;
    }

    public TestData addUserOther() throws Exception {
        addUser(userOther);
        return this;
    }

    public TestData addItemRequestPhone() throws Exception {
        itemRequestPhone.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        addItemRequest(itemRequestPhone, itemRequestPhone.getRequestor().getId());
        return this;
    }

    public TestData addItemRequestBook() throws Exception {
        itemRequestBook.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        addItemRequest(itemRequestBook, itemRequestBook.getRequestor().getId());
        return this;
    }

    public TestData addItemUnavailable() throws Exception {
        addItem(itemUnavailable, itemUnavailable.getOwner().getId());
        return this;
    }

    public TestData addItemPhoneWithRequest() throws Exception {
        addItem(itemPhoneWithRequest, itemPhoneWithRequest.getOwner().getId());
        return this;
    }

    public TestData addItemPhone4gWithRequest() throws Exception {
        addItem(itemPhone4gWithRequest, itemPhone4gWithRequest.getOwner().getId());
        return this;
    }

    public TestData addItemWithBookings() throws Exception {
        addItem(itemWithBookings, itemWithBookings.getOwner().getId());
        return this;
    }

    public TestData addBookingPastApproved() throws Exception {
        bookingPastApproved.setStart(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)
                .plusNanos(START_NANOS));
        bookingPastApproved.setEnd(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)
                .plusNanos(END_NANOS));
        addBooking(bookingPastApproved, bookingPastApproved.getBooker().getId());
        Thread.sleep(WAIT_PAST_MILLIS); //задержка, что бы у пользователя было завершённое бронирование
        return this;
    }

    public TestData addBookingCurrentApproved() throws Exception {
        bookingCurrentApproved.setStart(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)
                .plusNanos(START_NANOS));
        addBooking(bookingCurrentApproved, bookingCurrentApproved.getBooker().getId());
        Thread.sleep(WAIT_CURRENT_MILLIS); //задержка, что бы у пользователя было текущее бронирование
        return this;
    }

    public TestData addBookingFutureWaiting() throws Exception {
        addBooking(bookingFutureWaiting, bookingFutureWaiting.getBooker().getId());
        return this;
    }

    public TestData addBookingFutureRejected() throws Exception {
        addBooking(bookingFutureRejected, bookingFutureRejected.getBooker().getId());
        return this;
    }

    public TestData approveBookingPastApproved() throws Exception {
        approveBooking(bookingPastApproved.getId(), true, bookingPastApproved.getItem().getOwner().getId());
        return this;
    }

    public TestData approveBookingCurrentApproved() throws Exception {
        approveBooking(bookingCurrentApproved.getId(), true, bookingCurrentApproved.getItem().getOwner().getId());
        return this;
    }

    public TestData approveBookingFutureRejected() throws Exception {
        approveBooking(bookingFutureRejected.getId(), false, bookingFutureRejected.getItem().getOwner().getId());
        return this;
    }

    public TestData addCommentText() throws Exception {
        commentText.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        addComment(commentText, commentText.getItem().getId(), commentText.getAuthor().getId());
        return this;
    }

    public TestData addCommentEmoji() throws Exception {
        commentEmoji.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        addComment(commentEmoji, commentEmoji.getItem().getId(), commentEmoji.getAuthor().getId());
        return this;
    }
}
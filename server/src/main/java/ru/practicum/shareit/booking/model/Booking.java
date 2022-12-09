package ru.practicum.shareit.booking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.element.model.Element;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@ToString
@Table(name = "bookings")
@Getter
@Setter
public class Booking extends Element {
    /**
     * уникальный идентификатор бронирования
     */
    @Id
    @GeneratedValue(generator = "booking_seq")
    @SequenceGenerator(name = "booking_seq", sequenceName = "booking_seq", allocationSize = 1)
    @Column(name = "booking_id", nullable = false)
    private Long id;
    /**
     * дата и время начала бронирования
     */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime start;
    /**
     * дата и время конца бронирования
     */
    @Column(name = "end_date", nullable = false)
    private LocalDateTime end;
    /**
     * вещь, которую пользователь бронирует
     */
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false)
    private Item item;
    /**
     * пользователь, который осуществляет бронирование
     */
    @ManyToOne
    @JoinColumn(name = "booker_id", referencedColumnName = "user_id", nullable = false)
    private User booker;
    /**
     * статус бронирования
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;
}

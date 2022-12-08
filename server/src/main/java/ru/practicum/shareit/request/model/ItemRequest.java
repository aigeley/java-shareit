package ru.practicum.shareit.request.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.element.model.Element;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@ToString
@Table(name = "requests")
@Getter
@Setter
public class ItemRequest extends Element {
    /**
     * уникальный идентификатор запроса
     */
    @Id
    @GeneratedValue(generator = "request_seq")
    @SequenceGenerator(name = "request_seq", sequenceName = "request_seq", allocationSize = 1)
    @Column(name = "request_id", nullable = false)
    private Long id;
    /**
     * текст запроса, содержащий описание требуемой вещи
     */
    @Column(name = "description", length = 512, nullable = false)
    private String description;
    /**
     * пользователь, создавший запрос
     */
    @ManyToOne
    @JoinColumn(name = "requestor_id", referencedColumnName = "user_id", nullable = false)
    private User requestor;
    /**
     * дата и время создания запроса
     */
    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
}

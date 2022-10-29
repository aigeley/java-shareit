package ru.practicum.shareit.request.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
public class ItemRequest {
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requestor_id", referencedColumnName = "user_id", nullable = false)
    private User requestor;
    /**
     * дата и время создания запроса
     */
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}

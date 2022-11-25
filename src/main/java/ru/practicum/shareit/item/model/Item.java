package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.element.model.Identifiable;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@ToString
@Table(name = "items")
@Getter
@Setter
public class Item implements Identifiable {
    /**
     * уникальный идентификатор вещи
     */
    @Id
    @GeneratedValue(generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    @Column(name = "item_id", nullable = false)
    private Long id;
    /**
     * краткое название
     */
    @Column(name = "item_name", nullable = false)
    private String name;
    /**
     * развёрнутое описание
     */
    @Column(name = "description", length = 512, nullable = false)
    private String description;
    /**
     * статус о том, доступна или нет вещь для аренды
     */
    @Column(name = "is_available", nullable = false)
    private Boolean available;
    /**
     * владелец вещи
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false)
    private User owner;
    /**
     * если вещь была создана по запросу другого пользователя,
     * то в этом поле будет храниться ссылка на соответствующий запрос
     */
    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "request_id")
    private ItemRequest request;
}

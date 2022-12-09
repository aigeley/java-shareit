package ru.practicum.shareit.item.model;

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
@Table(name = "comments")
@Getter
@Setter
public class Comment extends Element {
    /**
     * уникальный идентификатор комментария
     */
    @Id
    @GeneratedValue(generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
    @Column(name = "comment_id", nullable = false)
    private Long id;
    /**
     * содержимое комментария
     */
    @Column(name = "text", nullable = false)
    private String text;
    /**
     * вещь, к которой относится комментарий
     */
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false)
    private Item item;
    /**
     * автор комментария
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id", nullable = false)
    private User author;
    /**
     * дата создания комментария
     */
    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
}

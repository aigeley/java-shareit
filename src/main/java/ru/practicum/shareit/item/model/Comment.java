package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.element.model.Identifiable;
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
@Table(name = "comments")
@Getter
@Setter
public class Comment implements Identifiable {
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false)
    private Item item;
    /**
     * автор комментария
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id", nullable = false)
    private User author;
    /**
     * дата создания комментария
     */
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}

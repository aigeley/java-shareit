package ru.practicum.shareit.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.element.model.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@ToString
@Table(name = "users")
@Getter
@Setter
public class User implements Identifiable {
    /**
     * уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Long id;
    /**
     * имя или логин пользователя
     */
    @Column(name = "user_name", nullable = false)
    private String name;
    /**
     * адрес электронной почты
     */
    @Column(name = "email", length = 512, nullable = false, unique = true)
    private String email;
}

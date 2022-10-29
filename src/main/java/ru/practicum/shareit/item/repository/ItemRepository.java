package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwner_IdOrderById(long userId);

    @Query("SELECT i FROM Item i " +
            "WHERE i.available = true " +
            "AND (UPPER(i.name) LIKE UPPER(CONCAT('%', ?1, '%')) " +
            "OR UPPER(i.description) LIKE UPPER(CONCAT('%', ?1, '%'))) " +
            "AND ?1 IS NOT NULL " +
            "AND ?1 <> ''")
    List<Item> search(String text);
}

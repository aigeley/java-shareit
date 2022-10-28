package ru.practicum.shareit.item.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.shareit.item.model.Item;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private static String contains(String text) {
        return MessageFormat.format("%{0}%", StringUtils.toRootUpperCase(text));
    }

    public static Specification<Item> itemNameLike(String name) {
        return (root, query, builder) -> builder.like(builder.upper(root.get("name")), contains(name));
    }

    public static Specification<Item> itemDescriptionLike(String description) {
        return (root, query, builder) -> builder.like(builder.upper(root.get("description")), contains(description));
    }

    public static Specification<Item> itemIsAvailable() {
        return (root, query, builder) -> builder.equal(root.get("available"), true);
    }

    private final ItemRepository itemRepository;

    public ItemRepositoryCustomImpl(@Lazy ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> search(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        Specification<Item> specification = Specification
                .where(itemNameLike(text))
                .or(itemDescriptionLike(text))
                .and(itemIsAvailable());

        return itemRepository.findAll(specification, Sort.by(Order.asc("id")));
    }
}

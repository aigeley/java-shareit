package ru.practicum.shareit.element.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.Objects;

public class PageRequestFromElement extends PageRequest {
    private final int from;

    protected PageRequestFromElement(int from, int size, Sort sort) {
        super(from / size, size, sort);
        this.from = from;
    }

    public static PageRequestFromElement of(int from, int size, Sort sort) {
        return new PageRequestFromElement(from, size, sort);
    }

    public static PageRequestFromElement ofSortByIdAsc(int from, int size) {
        return of(from, size, Sort.by(Order.asc("id")));
    }

    public static PageRequestFromElement ofSortByIdDesc(int from, int size) {
        return of(from, size, Sort.by(Order.desc("id")));
    }

    @Override
    public long getOffset() {
        return from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PageRequestFromElement)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        PageRequestFromElement that = (PageRequestFromElement) o;
        return from == that.from;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from);
    }
}

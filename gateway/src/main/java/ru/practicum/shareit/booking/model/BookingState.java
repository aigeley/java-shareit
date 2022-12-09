package ru.practicum.shareit.booking.model;

public enum BookingState {
    /**
     * все
     */
    ALL,
    /**
     * текущие
     */
    CURRENT,
    /**
     * завершённые
     */
    PAST,
    /**
     * будущие
     */
    FUTURE,
    /**
     * ожидающие подтверждения
     */
    WAITING,
    /**
     * отклонённые
     */
    REJECTED
}

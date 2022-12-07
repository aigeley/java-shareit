package ru.practicum.shareit.booking.model.validation;

import ru.practicum.shareit.booking.model.BookingDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingStartLessThenEndValidator implements ConstraintValidator<BookingStartLessThenEnd, BookingDto> {
    @Override
    public boolean isValid(BookingDto bookingDto, ConstraintValidatorContext context) {
        return bookingDto.getStart().isBefore(bookingDto.getEnd());
    }

}
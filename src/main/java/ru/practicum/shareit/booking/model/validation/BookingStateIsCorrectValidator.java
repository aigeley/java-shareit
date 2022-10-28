package ru.practicum.shareit.booking.model.validation;

import ru.practicum.shareit.booking.model.BookingState;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingStateIsCorrectValidator implements ConstraintValidator<BookingStateIsCorrect, String> {
    @Override
    public boolean isValid(String state, ConstraintValidatorContext context) {
        try {
            BookingState.valueOf(state);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

}
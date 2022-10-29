package ru.practicum.shareit.booking.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = BookingStartLessThenEndValidator.class)
@Documented
public @interface BookingStartLessThenEnd {

    String message() default "дата начала бронирования должна быть раньше даты конца бронирования";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
package ru.otus.homework.users.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserNameUniqueValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNameUniqueConstraint {
    String message() default "\"{field}\" must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

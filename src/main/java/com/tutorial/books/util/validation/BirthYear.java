package com.tutorial.books.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BirthYearValidator.class)
@Documented
public @interface BirthYear {
    String message() default "{Year.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

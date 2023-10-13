package com.tutorial.books.util.validation.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    private static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    //TODO: check against dictionary?
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        else
            return value.length() >= MIN_PASSWORD_LENGTH;
    }
}

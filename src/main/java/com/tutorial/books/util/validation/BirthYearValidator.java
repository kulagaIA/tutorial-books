package com.tutorial.books.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;

public class BirthYearValidator implements ConstraintValidator<BirthYear, Integer> {

    private static final Integer MIN_BIRTH_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 130;

    @Override
    public void initialize(BirthYear constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value != null)
            return value >= MIN_BIRTH_YEAR && value <= Calendar.getInstance().get(Calendar.YEAR);
        else
            return false;
    }
}

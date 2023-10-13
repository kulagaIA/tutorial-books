package com.tutorial.books.util.validation.user;

import com.tutorial.books.repository.impl.UserJpaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public void initialize(UsernameValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    //TODO: handle exception so that if username is taken before saving to db but after validation, user sees adequate message
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.isEmpty())
            return userJpaRepository.findByUsername(value).isEmpty();
        else
            return false;
    }
}

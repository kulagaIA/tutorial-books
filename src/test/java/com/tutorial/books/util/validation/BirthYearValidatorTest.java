package com.tutorial.books.util.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("junit")
public class BirthYearValidatorTest {

    private final BirthYearValidator birthYearValidator = new BirthYearValidator();

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testYearMoreThanMax() {
        assertThat(birthYearValidator.isValid(Calendar.getInstance().get(Calendar.YEAR) + 1, context)).isFalse();
    }

    @Test
    void testYearLessThanMin() {
        assertThat(birthYearValidator.isValid(Calendar.getInstance().get(Calendar.YEAR) - 131, context)).isFalse();
    }

    @Test
    void testValid() {
        assertThat(birthYearValidator.isValid(2000, context)).isTrue();
    }

    @Test
    void testNull() {
        assertThat(birthYearValidator.isValid(null, context)).isFalse();
    }

    @Test
    void testMinusBigNumber() {
        assertThat(birthYearValidator.isValid(-32767, context)).isFalse();
    }
}

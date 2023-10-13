package com.tutorial.books.dto;

import com.tutorial.books.util.validation.user.BirthYearValidation;
import com.tutorial.books.util.validation.user.PasswordValidation;
import com.tutorial.books.util.validation.user.UsernameValidation;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

import static com.tutorial.books.util.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserCreateDTO {

    @NotEmpty(message = USER_NAME_VALIDATION_ERROR)
    private String name;

    @BirthYearValidation(message = USER_BIRTH_YEAR_VALIDATION_ERROR)
    private int birthYear;

    @UsernameValidation(message = USER_USERNAME_VALIDATION_ERROR)
    private String username;

    @PasswordValidation(message = USER_PASSWORD_VALIDATION_ERROR)
    private String password;

    private List<String> confirmationTypes;

}
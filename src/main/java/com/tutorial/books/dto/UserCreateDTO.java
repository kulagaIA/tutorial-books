package com.tutorial.books.dto;

import com.tutorial.books.util.validation.BirthYear;
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

    @BirthYear(message = USER_BIRTH_YEAR_VALIDATION_ERROR)
    private int birthYear;

    @NotEmpty(message = USER_USERNAME_VALIDATION_ERROR)
    private String username;

    @NotEmpty(message = USER_PASSWORD_VALIDATION_ERROR)
    private String password;

    private List<String> confirmationTypes;

}
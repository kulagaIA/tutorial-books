package com.tutorial.books.entity;

import com.tutorial.books.util.validation.BirthYear;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import static com.tutorial.books.util.Constants.USER_BIRTH_YEAR_VALIDATION_ERROR;
import static com.tutorial.books.util.Constants.USER_NAME_VALIDATION_ERROR;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class User {

    private Integer id;

    @NotEmpty(message = USER_NAME_VALIDATION_ERROR)
    private String name;

    @BirthYear(message = USER_BIRTH_YEAR_VALIDATION_ERROR)
    private Integer birthYear;

}

package com.tutorial.books.entity;

import com.tutorial.books.util.validation.BirthYear;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty(message = USER_NAME_VALIDATION_ERROR)
    private String name;

    @BirthYear(message = USER_BIRTH_YEAR_VALIDATION_ERROR)
    private Integer birthYear;

}

package com.tutorial.books.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class User {

    private Integer id;

    @NotEmpty
    private String name;

    @Min(0)
    private Integer birthYear;

}

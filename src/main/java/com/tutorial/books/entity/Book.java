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
public class Book {

    private Integer id;

    @NotEmpty
    private String name;

    private String author;

    @Min(0)
    private Integer publishYear;

    @Min(0)
    private Integer quantityAvailable;
}

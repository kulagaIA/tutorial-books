package com.tutorial.books.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.tutorial.books.util.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Book {

    private Integer id;

    @NotEmpty(message = BOOK_NAME_VALIDATION_ERROR)
    private String name;

    private String author;

    @Min(value = 0, message = BOOK_PUBLISH_YEAR_VALIDATION_ERROR)
    @NotNull(message = BOOK_PUBLISH_YEAR_VALIDATION_ERROR)
    private Integer publishYear;

    @Min(value = 0, message = BOOK_QUANTITY_AVAILABLE_VALIDATION_ERROR)
    @NotNull(message = BOOK_QUANTITY_AVAILABLE_VALIDATION_ERROR)
    private Integer quantityAvailable;
}

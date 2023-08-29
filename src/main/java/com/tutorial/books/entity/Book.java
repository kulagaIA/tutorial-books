package com.tutorial.books.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

import static com.tutorial.books.util.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @EqualsAndHashCode.Exclude
    private Set<User> users;
}

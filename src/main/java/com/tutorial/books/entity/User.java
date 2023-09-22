package com.tutorial.books.entity;

import com.tutorial.books.util.validation.BirthYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = USER_NAME_VALIDATION_ERROR)
    private String name;

    @BirthYear(message = USER_BIRTH_YEAR_VALIDATION_ERROR)
    private Integer birthYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @EqualsAndHashCode.Exclude
    private Set<Book> books;

    @NotEmpty(message = USER_USERNAME_VALIDATION_ERROR)
    private String username;

    @NotEmpty(message = USER_PASSWORD_VALIDATION_ERROR)
    private String password;

    private String salt;
}

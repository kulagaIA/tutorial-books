package com.tutorial.books.entity;

import com.tutorial.books.util.validation.BirthYear;
import com.tutorial.books.util.validation.groups.CreateUserInfo;
import com.tutorial.books.util.validation.groups.UpdateUserInfo;
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

    @NotEmpty(
            groups = {CreateUserInfo.class, UpdateUserInfo.class},
            message = USER_NAME_VALIDATION_ERROR)
    private String name;

    @BirthYear(
            groups = {CreateUserInfo.class, UpdateUserInfo.class},
            message = USER_BIRTH_YEAR_VALIDATION_ERROR)
    private Integer birthYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @EqualsAndHashCode.Exclude
    private Set<Book> books;

    @NotEmpty(groups = CreateUserInfo.class, message = USER_USERNAME_VALIDATION_ERROR)
    private String username;

    @NotEmpty(groups = CreateUserInfo.class, message = USER_PASSWORD_VALIDATION_ERROR)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;
}

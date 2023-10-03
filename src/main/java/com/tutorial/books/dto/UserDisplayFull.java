package com.tutorial.books.dto;

import com.tutorial.books.entity.Book;
import com.tutorial.books.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserDisplayFull {

    private int id;

    private String name;

    private int birthYear;

    private String username;

    private Set<Book> books;

    private Set<Role> roles;

}

package com.tutorial.books.dto;

import com.tutorial.books.entity.Book;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserDisplay {

    private String name;

    private int birthYear;

    private String username;

    private Set<Book> books;

}

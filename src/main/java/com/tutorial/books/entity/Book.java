package com.tutorial.books.entity;

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

    private String name;

    private String author;

    private Integer year;
}

package com.tutorial.books.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private Integer id;

    private String name;

    private Integer birthYear;

}

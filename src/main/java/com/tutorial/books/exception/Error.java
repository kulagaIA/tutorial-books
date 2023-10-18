package com.tutorial.books.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Error {

    private String status;

    private String stacktrace;
}

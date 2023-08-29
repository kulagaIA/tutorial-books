package com.tutorial.books.util;

public class Constants {

    public static final String TABLE_NAME_USERS = "users";

    public static final String COLUMN_NAME_USERS_ID = "id";

    public static final String TABLE_NAME_BOOKS = "books";

    public static final String COLUMN_NAME_BOOKS_ID = "id";

    public static final String TABLE_NAME_USERS_BOOKS = "users_books";

    public static final String BOOK_NAME_VALIDATION_ERROR = "book name must not be empty";

    public static final String BOOK_PUBLISH_YEAR_VALIDATION_ERROR = "book publish year must exist and must be more than 0";

    public static final String BOOK_QUANTITY_AVAILABLE_VALIDATION_ERROR = "quantity of available books must exist and must be minimum 0";

    public static final String USER_NAME_VALIDATION_ERROR = "user name must not be empty";

    public static final String USER_BIRTH_YEAR_VALIDATION_ERROR = "user birth year must exist " +
            "and must be more than 1899 and no more than current year";

    public static final Integer HTTP_STATUS_UNPROCESSABLE_CONTENT = 422;
}

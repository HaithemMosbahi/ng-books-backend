package com.demo.books.model;

import java.util.List;
import lombok.*;

/**
 * Represents User of the application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Long id;
    private String login;
    private String password;
    private List<Book> favoriteBooks;
}

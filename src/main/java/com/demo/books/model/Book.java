package com.demo.books.model;

import lombok.*;


/**
 * Represents the Book model
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    private Long id;
    private String title;
    private String description;
}

package com.demo.books.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

/**
 * Represents User of the application
 */
@Entity
@Data
@NoArgsConstructor
@ToString
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NonNull
    private Long id;
    private String login;
    private String password;
    //private List<Book> favoriteBooks;
}

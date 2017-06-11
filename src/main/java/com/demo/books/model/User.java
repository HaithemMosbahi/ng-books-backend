package com.demo.books.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.*;

/**
 * Represents User of the application
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@NonNull
    private String login;
    private String password;
    private String firstNAme;
    private String lastName;
    @ManyToMany(fetch=FetchType.EAGER)
	@NonNull
    private Set<Authority> authorities;
    //private List<Book> favoriteBooks;
}

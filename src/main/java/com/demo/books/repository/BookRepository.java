package com.demo.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.books.model.Book;

/**
 * Spring Data JPA Repository for the Book entity
 * 
 * @author hmosbahi
 *
 */
public interface BookRepository extends JpaRepository<Book, Long>{

}

package com.demo.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.books.model.Authority;

/**
 * Spring Data JPA Repository for the Authority entity
 * 
 * @author hmosbahi
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
}
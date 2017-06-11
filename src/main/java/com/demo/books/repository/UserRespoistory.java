package com.demo.books.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.books.model.User;

/**
 * Spring Data JPA Repository for the User entity
 * 
 * @author hmosbahi
 *
 */
public interface UserRespoistory extends JpaRepository<User, Long> {
	
	Optional<User> findByLogin(String login);

}

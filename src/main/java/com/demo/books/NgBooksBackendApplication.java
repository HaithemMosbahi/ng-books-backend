package com.demo.books;


import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.books.model.Authority;
import com.demo.books.model.Book;
import com.demo.books.model.User;
import com.demo.books.repository.AuthorityRepository;
import com.demo.books.repository.BookRepository;
import com.demo.books.repository.UserRespoistory;

/**
 * Application Entry point 
 * 
 * @author hmosbahi
 *
 */

@SpringBootApplication
public class NgBooksBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NgBooksBackendApplication.class, args);
	}
	
	@Bean
    CommandLineRunner dummyData(BookRepository bookRepository,
    		         UserRespoistory userRespoistory,AuthorityRepository authorityRepository) {
    	return args -> {
    		
    		authorityRepository.deleteAll();
    		bookRepository.deleteAll();
    		userRespoistory.deleteAll();


    		Stream.of("user","admin").map(Authority::new).forEach(authorityRepository::save);
    		Stream.of("Think And Grow Rich","Man's search for meaning","The 33 strategies of war")
    		      .map(Book::new)
    		      .forEach(bookRepository::save);
    		
    		Set<Authority> authorities = new HashSet<>(Arrays.asList(authorityRepository.findOne("user")));
           
    		Stream.of("userBook1","userBook2","userBook3")
    		      .map(username -> new User(username, authorities))
    		      .forEach(userRespoistory::save);
    	
    		
    		
    	};
    }
	

}


@RestController
@RequestMapping("home")
class UserResource {
	
	
	@GetMapping
	public String autheticated(Principal principal) {
		return principal.getName();
	}
	
}

@RestController
@RequestMapping("books")
class BookHandler {
	
	private final BookRepository bookRepository;
	
	public BookHandler(BookRepository bookRepository){
		this.bookRepository = bookRepository;
	}

	@GetMapping
	public List<Book> all() {
		return bookRepository.findAll();
	}

	@GetMapping(path="/{id}")
	public Book byId(@PathVariable Long id) {
		return bookRepository.findOne(id);
	}

}

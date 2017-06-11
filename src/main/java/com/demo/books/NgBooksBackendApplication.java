package com.demo.books;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.books.model.Authority;
import com.demo.books.model.Book;
import com.demo.books.model.User;
import com.demo.books.repository.AuthorityRepository;
import com.demo.books.repository.BookRepository;
import com.demo.books.repository.UserRespoistory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class NgBooksBackendApplication implements CommandLineRunner {

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
    		
    		Set<Authority> authorities = new HashSet<>(Arrays.asList(authorityRepository.findById("user").get()));
           
    		Stream.of("userBook1","userBook2","userBook3")
    		      .map(username -> new User(username, authorities))
    		      .forEach(userRespoistory::save);
    	
    		
    		
    	};
    }
	

	/**
	 * Represents api routes based on the brand new Web Functional framework
	 * 
	 * @param bookHandler
	 * @return
	 */
	@Bean
	RouterFunction<?> routerFunction(BookHandler bookHandler) {
		return route(GET("/books"), bookHandler::all).andRoute(GET("/books/{id}"), bookHandler::byId);
	}



	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

@Component
class BookHandler {
	
	private final BookRepository bookRepository;
	
	public BookHandler(BookRepository bookRepository){
		this.bookRepository = bookRepository;
	}

	Mono<ServerResponse> all(ServerRequest request) {
		return ok().body(Flux.just(bookRepository.findAll().toArray(new Book[3])), Book.class);
	}

	Mono<ServerResponse> byId(ServerRequest request) {
		return ok()
				.body(Mono.justOrEmpty(bookRepository.findById(Long.parseLong(request.pathVariable("id")))),
				 Book.class);
	}

}

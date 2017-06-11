package com.demo.books;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.books.model.Book;
import com.demo.books.repository.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class NgBooksBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NgBooksBackendApplication.class, args);
	}
	
	@Bean
    CommandLineRunner dummyData(BookRepository bookRepository) {
    	return args -> {
    		bookRepository.save(new Book((long)1,"Think And Grow Rich"));
    		bookRepository.save(new Book((long)2,"Man's search for meaning"));
    		bookRepository.save(new Book((long)3,"The 33 strategies of war"));
    		
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

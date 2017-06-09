package com.demo.books;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.books.model.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SpringBootApplication
public class NgBooksBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NgBooksBackendApplication.class, args);
	}
	
	  @Bean
	  RouterFunction<?> routerFunction(BookHandler bookHandler) {
	     return route(GET("/books"), bookHandler::all)
	    		 .andRoute(GET("/books/{id}"), bookHandler::byId);
	  }
	
	
}

@Component
class BookHandler{
	
	
	Mono<ServerResponse> all(ServerRequest request){
		return ok().body(Flux.just(new Book(1,"think and grow rich","desc"),new Book(2,"Man's search for meaning","there")), Book.class);
	}
	
	Mono<ServerResponse> byId(ServerRequest request) {
        return ok().body(new Book(1,"think and grow rich","desc"), Book.class);
    }
	
}

package com.demo.books.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.books.model.Book;
import com.demo.books.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;


/*@Service
public class BookService {
	
	private final BookRepository bookRepository;
	private final RestTemplate restTemplate;
	@Value("${books.api.url}")
	private String booksApi;

	BookService(BookRepository bookRepository,RestTemplate restTemplate) {
		this.bookRepository = bookRepository;
		this.restTemplate = restTemplate;
	}
	
	public Flux<Book> allBooks(){
		JsonNode data = restTemplate.getForObject(booksApi, JsonNode.class);
		JsonNode books = data.get("items");
		return Flux.just(null);
	}
	
	

}*/

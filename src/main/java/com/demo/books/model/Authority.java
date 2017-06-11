package com.demo.books.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Authority {
	
	@Id
	@NotNull
	private String name;

}

package com.demo.books.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.books.model.Authority;
import com.demo.books.model.User;
import com.demo.books.repository.UserRespoistory;

@Service
public class UserService {

	private final UserRespoistory userRespoistory;

	public UserService(UserRespoistory userRespoistory) {
		super();
		this.userRespoistory = userRespoistory;
	}

	public Set<String> getUserAuhorities(String userName) {
		Optional<User> user = userRespoistory.findByLogin(userName);
		if (user.isPresent()) {
			return user.get()
				       .getAuthorities().stream()
				       .map(Authority::getName)
				       .collect(Collectors.toSet());

		}
		return Collections.emptySet();
	}

}

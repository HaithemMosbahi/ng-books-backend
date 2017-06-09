package com.demo.books.config;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepository;
import org.springframework.security.authentication.UserDetailsRepositoryAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.server.WebFilter;

import reactor.core.publisher.Mono;

/**
 * Security configuration class 
 * 
 * @author hmosbahi
 *
 */
@Configuration
public class SecurityConfiguration {

    public static final String AUTHORITY_USER = "user";

    private Map<String, List<String>> users = new ConcurrentHashMap<String, List<String>>() {
        {
            put("user1", asList(AUTHORITY_USER));
            put("user2", asList(AUTHORITY_USER));
       
        }
    };
    
    @Bean
    UserDetailsRepository userDetailsRepository(){
    	return username -> Mono.justOrEmpty(users.get(username))
                .flatMapIterable(Function.identity())
                .map(SimpleGrantedAuthority::new)
                .collectList()
                .map(grantedAuthorities -> new User(username, "password", grantedAuthorities));
    }
    
    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new UserDetailsRepositoryAuthenticationManager(userDetailsRepository());
    }

    @Bean
    WebFilter reactive(ReactiveAuthenticationManager manager) throws Exception {
        HttpSecurity http = HttpSecurity.http();
        http.authenticationManager(manager).httpBasic();
        http.authorizeExchange().antMatchers("/**").access(this::authorize);
        return http.build();
    }
    
    private Mono<AuthorizationDecision> authorize(Mono<Authentication> authentication, AuthorizationContext ctx) {
        return authentication
                .flatMapIterable(Authentication::getAuthorities)
                .any(ga -> ga.getAuthority().equalsIgnoreCase(AUTHORITY_USER))
                .map(AuthorizationDecision::new);
    }

}


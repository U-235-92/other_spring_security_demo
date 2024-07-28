package com.other.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.other.app.entity.Permition;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> 
				authorize
					.requestMatchers("app/create_item").hasAuthority(Permition.CREATE.name())
					.requestMatchers("app/update_item/**").hasAnyAuthority(Permition.CREATE.name(), Permition.UPDATE.name())
					.requestMatchers("app/delete_item/**").hasAuthority(Permition.DELETE.name())
					.requestMatchers(HttpMethod.GET).permitAll()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean 
	protected InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		UserDetails readUser = User
				.withUsername("R")
				.password(passwordEncoder().encode("123"))
				.authorities(Permition.READ.name())
				.build();
		UserDetails readCreateUser = User
				.withUsername("CR")
				.password(passwordEncoder().encode("123"))
				.authorities(Permition.READ.name(), Permition.CREATE.name())
				.build();
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		inMemoryUserDetailsManager.createUser(readUser);
		inMemoryUserDetailsManager.createUser(readCreateUser);
		return inMemoryUserDetailsManager;
	}
}

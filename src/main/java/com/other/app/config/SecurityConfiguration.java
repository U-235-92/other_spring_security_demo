package com.other.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/app/create_item").hasAuthority(Permition.CREATE.name())
				.requestMatchers("/app/update_item/**").hasAnyAuthority(Permition.CREATE.name(), Permition.UPDATE.name())
				.requestMatchers("/app/delete_item/**").hasAuthority(Permition.DELETE.name())
				.requestMatchers("/app/userdetails").authenticated()
				.requestMatchers("/app/get_item/**").permitAll()
				.requestMatchers("/app/get_items").permitAll()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean 
	protected InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		UserDetails readUser = User.builder()
				.username("r")
				.password(passwordEncoder().encode("123"))
				.authorities(Permition.READ.name())
				.build();
		UserDetails readCreateUser = User.builder()
				.username("rc")
				.password(passwordEncoder().encode("123"))
				.authorities(Permition.READ.name(), Permition.CREATE.name())
				.build();
		UserDetails readCreateDeleteUser = User.builder()
				.username("rcd")
				.password(passwordEncoder().encode("123"))
				.authorities(Permition.READ.name(), Permition.CREATE.name(), Permition.DELETE.name())
				.build();
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		inMemoryUserDetailsManager.createUser(readUser);
		inMemoryUserDetailsManager.createUser(readCreateUser);
		inMemoryUserDetailsManager.createUser(readCreateDeleteUser);
		return inMemoryUserDetailsManager;
	}
}

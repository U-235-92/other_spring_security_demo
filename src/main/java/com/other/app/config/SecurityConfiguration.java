package com.other.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import com.other.app.service.ClientService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final ClientService clientService;
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrfConfigurer -> csrfConfigurer.disable())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/app/create_item").hasAuthority(Permition.CREATE.name())
				.requestMatchers("/app/update_item/**").hasAnyAuthority(Permition.CREATE.name(), Permition.UPDATE.name())
				.requestMatchers("/app/delete_item/**").hasAuthority(Permition.DELETE.name())
				.requestMatchers("/app/test_post").hasAuthority(Permition.READ.name())
				.requestMatchers("/app/userdetails").authenticated()
				.requestMatchers("/app/get_item/**").permitAll()
				.requestMatchers("/app/get_items").permitAll()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(clientService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
//	@Bean 
//	protected InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//		UserDetails readUser = User.builder()
//				.username("r")
//				.password(passwordEncoder().encode("123"))
//				.authorities(Permition.READ.name())
//				.build();
//		UserDetails readCreateUser = User.builder()
//				.username("cr")
//				.password(passwordEncoder().encode("123"))
//				.authorities(Permition.READ.name(), Permition.CREATE.name())
//				.build();
//		UserDetails readCreateDeleteUser = User.builder()
//				.username("crd")
//				.password(passwordEncoder().encode("123"))
//				.authorities(Permition.READ.name(), Permition.CREATE.name(), Permition.DELETE.name())
//				.build();
//		UserDetails readCreateDeleteUpdateUser = User.builder()
//				.username("crud")
//				.password(passwordEncoder().encode("123"))
//				.authorities(Permition.READ.name(), Permition.CREATE.name(), Permition.DELETE.name(), Permition.UPDATE.name())
//				.build();
//		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//		inMemoryUserDetailsManager.createUser(readUser);
//		inMemoryUserDetailsManager.createUser(readCreateUser);
//		inMemoryUserDetailsManager.createUser(readCreateDeleteUser);
//		inMemoryUserDetailsManager.createUser(readCreateDeleteUpdateUser);
//		return inMemoryUserDetailsManager;
//	}
}

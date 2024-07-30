package com.other.app.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.other.app.entity.Client;
import com.other.app.repository.ClientReository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService implements UserDetailsService {

	private final ClientReository clientReository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client client = clientReository.findClientByName(username);
		if(client == null) {
			String message = String.format("The user %s not found", username);
			throw new UsernameNotFoundException(message);
		}
		return fromClient(client);
	}
	
	private UserDetails fromClient(Client client) {
		return new User(client.getName(), 
						client.getPassword(), 
						client.getPermitions()
							.stream()
							.map(permition -> new SimpleGrantedAuthority(permition.name()))
							.collect(Collectors.toList()));
	}
}

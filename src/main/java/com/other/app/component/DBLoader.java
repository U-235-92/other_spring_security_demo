package com.other.app.component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.other.app.entity.Client;
import com.other.app.entity.Item;
import com.other.app.entity.Permition;
import com.other.app.repository.ClientReository;
import com.other.app.repository.DbItemRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DBLoader {

	private final DbItemRepository dbItemRepository;
	private final ClientReository clientReository;
	private final PasswordEncoder passwordEncoder;
	
	@PostConstruct
	@Priority(value = 2)
	protected void addItems() {
		dbItemRepository.saveAll(Arrays.asList(new Item(1l, "Book 1"), new Item(2l, "Book 2")));
	}
	
	@PostConstruct
	@Priority(value = 1)
	protected void addClients() {
		Client r = new Client();
		r.setName("r");
		r.setPassword(passwordEncoder.encode("123"));
		r.setPermitions(Set.of(Permition.READ));
		Client cr = new Client();
		cr.setName("cr");
		cr.setPassword(passwordEncoder.encode("123"));
		cr.setPermitions(Set.of(Permition.CREATE, Permition.READ));
		Client crud = new Client();
		crud.setName("crud");
		crud.setPassword(passwordEncoder.encode("123"));
		crud.setPermitions(Set.of(Permition.CREATE, Permition.READ, Permition.UPDATE, Permition.DELETE));
		clientReository.saveAll(List.of(r, cr, crud));
	}
}

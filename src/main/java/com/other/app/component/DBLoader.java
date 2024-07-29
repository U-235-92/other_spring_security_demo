package com.other.app.component;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.other.app.entity.Item;
import com.other.app.repository.DbItemRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DBLoader {

	private final DbItemRepository dbItemRepository;
	
	@PostConstruct
	public void addItems() {
		dbItemRepository.saveAll(Arrays.asList(new Item(1l, "Book 1"), new Item(2l, "Book 2")));
	}
}

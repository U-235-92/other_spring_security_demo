package com.other.app.service;

import org.springframework.stereotype.Service;

import com.other.app.dto.ItemDTO;
import com.other.app.entity.Item;
import com.other.app.repository.InMemoryItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final InMemoryItemRepository inMemoryItemRepository;
	
	public Item getItem(long id) {
		return inMemoryItemRepository.getItem(id);
	}
	
	public void deleteItem(long id) {
		inMemoryItemRepository.deleteItem(id);
	}
	
	public void createItem(ItemDTO itemDTO) {
		inMemoryItemRepository.createItem(itemDTO);
	}
	
	public void updateItem(long id, ItemDTO itemDTO) {
		inMemoryItemRepository.updateItem(id, itemDTO);
	}
}

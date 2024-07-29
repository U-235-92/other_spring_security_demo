package com.other.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.other.app.dto.ItemDTO;
import com.other.app.entity.Item;
import com.other.app.repository.DbItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final DbItemRepository dbItemRepository;
	
	public Item getItem(long id) {
		return dbItemRepository.getReferenceById(id);
	}
	
	public List<Item> getItems() {
		return dbItemRepository.findAll();
	}
	
	public void deleteItem(long id) {
		dbItemRepository.deleteById(id);
	}
	
	public void createItem(ItemDTO itemDTO) {
		Item item = new Item();
		item.setTitle(itemDTO.getTitle());
		dbItemRepository.save(item);
	}
	
	@Transactional
	public void updateItem(long id, ItemDTO itemDTO) {
		Item item = getItem(id);
		item.setTitle(itemDTO.getTitle());
		dbItemRepository.save(item);
//		dbItemRepository.updateItem(item.getId(), item.getTitle());
	}

}

package com.other.app.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.other.app.dto.ItemDTO;
import com.other.app.entity.Item;

@Repository
public class InMemoryItemRepository {

	private Map<Long, Item> itemMap = new HashMap<>();
	private static long id;
	
	{
		itemMap.put(id++, new Item(id, "Book 1"));
		itemMap.put(id++, new Item(id, "Book 2"));
	}
	
	public void createItem(ItemDTO itemDTO) {
		itemMap.put(id++, new Item(id, itemDTO.getTitle()));
	}
	
	public void deleteItem(long id) {
		itemMap.remove(id);
	}
	
	public Item getItem(long id) {
		return itemMap.get(id);
	}
	
	public void updateItem(long id, ItemDTO itemDTO) {
		Item item = getItem(id);
		item.setTitle(itemDTO.getTitle());
		itemMap.replace(id, item);		
	}
	
}

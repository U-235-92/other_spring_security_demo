package com.other.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.other.app.dto.ItemDTO;
import com.other.app.entity.Item;
import com.other.app.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class ItemController {

	private final ItemService itemService;
	
	@ResponseBody
	@GetMapping("/userdetails")
	public String getUserDetails() {
		return "User details:\n" + "name: " + SecurityContextHolder.getContext().getAuthentication().getName()
						+ ", authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
	}
	
	@PostMapping("/create_item")
	public ResponseEntity<String> createItem(@RequestBody ItemDTO itemDTO) {
		itemService.createItem(itemDTO);
		return ResponseEntity.ok("[Item created successful]");
	}
	
	@GetMapping("/delete_item/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable long id) {
		itemService.deleteItem(id);
		return ResponseEntity.ok("[Item deleted successful]");
	}
	
	@GetMapping("/get_item/{id}")
	public ResponseEntity<Item> getItem(@PathVariable long id) {
		return ResponseEntity.ok(itemService.getItem(id));
	}
	
	@GetMapping("/get_items")
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemService.getItems());
	}
	
	@PostMapping("/update_item/{id}")
	public ResponseEntity<String> updateItem(@PathVariable long id, @RequestBody ItemDTO itemDTO) {
		itemService.updateItem(id, itemDTO);
		return ResponseEntity.ok("[Item updated successful]");
	}
}

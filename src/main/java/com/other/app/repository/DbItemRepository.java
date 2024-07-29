package com.other.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.other.app.entity.Item;

public interface DbItemRepository extends JpaRepository<Item, Long> {

	@Modifying
	@Query(value = "UPDATE Item i SET i.title = :title WHERE i.id = :id")
	void updateItem(long id, String title);

}

package com.other.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.other.app.entity.Client;

public interface ClientReository extends JpaRepository<Client, Long> {

	public Client findClientByName(String name);
}

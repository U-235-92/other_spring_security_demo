package com.other.app.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

	@Id
	@GeneratedValue
	@Column(name = "client_id")
	private long id;
	@Column(name = "client_name")
	private String name;
	@Column(name = "client_passord")
	private String password;
	@ElementCollection
	@CollectionTable(name = "permitions")
	@Column(name = "permition")
	@Enumerated(EnumType.STRING)
	private Set<Permition> permitions = new HashSet<>();
}

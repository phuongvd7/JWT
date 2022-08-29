package com.example.demo.model;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String name;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY) 
	private List<Product> products;
	
	
}

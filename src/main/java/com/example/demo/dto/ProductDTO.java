package com.example.demo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data // can them anotaion data de get set qua mapper
public class ProductDTO {
	private int id;
	
	
	
	@NotEmpty
	private String name;
	
	@Min(0)
	private long price;
	
	private String description;
	
	// de trung ten category de map voi entity qua modelmapper
	private CategoryDTO category;
}

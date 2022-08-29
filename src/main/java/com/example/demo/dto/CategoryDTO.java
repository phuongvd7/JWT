package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CategoryDTO {
	private int id;
	
	@NotEmpty
	private String name;
	
	private int parentCateId;
}

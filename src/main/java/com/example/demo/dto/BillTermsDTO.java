package com.example.demo.dto;

import com.example.demo.model.Bill;
import com.example.demo.model.Product;
import com.example.demo.model.User;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data

@NoArgsConstructor
public class BillTermsDTO {
	private int id;
	private int quantity;
//	private double price;
	private Bill bill;
	private Product product;
}

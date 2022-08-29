package com.example.demo.dto;

import com.example.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchBillDTO {
	private int page;
	private int size;
	private String keyword;
	private String buyDate;
	private String couponCode;
	private User user;
	
	private String fromDate;
	private String toDate;
}


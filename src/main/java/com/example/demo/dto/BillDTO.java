package com.example.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.User;

import lombok.Data;

@Data
public class BillDTO {
	private int id;
	
	
	private User user;
	
	private String buyDate;
	
	private float discount;
	
	@NotEmpty
	private String couponCode;
}

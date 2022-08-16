package com.example.demo.dto;

import lombok.Data;

@Data
public class SignInForm {
	private String username;
	private String password;
	
	
	public SignInForm() {
		super();
	}


	public SignInForm(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	
}

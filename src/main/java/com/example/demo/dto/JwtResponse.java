package com.example.demo.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private Long id;
	private String token;
	private String type = "Bearer";
	private String name;
	private Collection<? extends GrantedAuthority> roles;
	
	public JwtResponse(String token, String name, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.name = name;
		this.roles = authorities;
	}
}

package com.example.demo.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.lang.Collections;

public class UserPrinciple implements UserDetails {
	private Long id;
	private String name;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private String avatar;
	private Collection<? extends GrantedAuthority> roles;
	public UserPrinciple() {
		super();
	}
	public UserPrinciple(Long id, String name, String username, String email, String password, String avatar,
			Collection<? extends GrantedAuthority> roles) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
		this.roles = roles;
	}
	
	public static UserPrinciple build(User user) { // boi vi thang author he thong la list nen can chuyen doi tu set sang list
		// cau lenh chuyen doi sang list
		 List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		 return new UserPrinciple(
				 user.getId(),
				 user.getName(),
				 user.getUsername(),
				 user.getEmail(),
				 user.getPassword(),
				 user.getAvatar(),
				 authorities
				 );
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
// gan du lieu cua he thong voi du lieu trong model
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

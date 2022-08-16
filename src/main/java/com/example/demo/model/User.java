package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = "email") }) // chan khong cho tao user va email trung nhau
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size
	private String name;
	@NotBlank
	@Size(min = 3, max =50)
	private String username;
	@NaturalId
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@JsonIgnore
	@NotBlank
	@Size(min = 6, max = 100)
	private String password;
	
	@Lob
	private String avatar;
	
	@ManyToMany(fetch = FetchType.EAGER) // de lazy thi no se khong truyen ra ngoai
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public User(Long id, @NotBlank @Size String name, @NotBlank @Size(min = 3, max = 50) String username,
			@NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 100) String password,
			String avatar, Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
		this.roles = roles;
	}

	public User(@NotBlank @Size(min = 3, max = 50) String name2, 
				@NotBlank @Size(min = 3, max = 50) String username2, 
				@NotBlank @Size( max = 50) @Email String email2, 
				@NotBlank @Size(min = 6, max = 100)String encode2) {
		this.name = name2;
		this.username = username2;
		this.email = email2;
		this.password = encode2;
	}
	
}

package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.User;

public interface UserService {
	Optional<User> findByUsername(String name); // tim kiem user trong db
	Boolean existsByUsername(String username); // username da co trong db chua, khi tao du lieu
	Boolean existsByEmail(String email);  // email da co trong db chua
	User save(User user);
}

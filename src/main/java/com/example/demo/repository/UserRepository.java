package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String name); // tim kiem user trong db
	Boolean existsByUsername(String username); // username da co trong db chua, khi tao du lieu
	Boolean existsByEmail(String email);  // email da co trong db chua
}

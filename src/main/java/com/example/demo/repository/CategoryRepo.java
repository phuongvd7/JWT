package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.model.Category;
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	@Query("SELECT u FROM Category u WHERE u.name LIKE :x ")
	Page<Category> searchByName(@Param("x") String s, Pageable pageable);
}

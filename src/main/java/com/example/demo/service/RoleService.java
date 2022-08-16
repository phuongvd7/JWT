package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.RoleName;


public interface RoleService {
	Optional<Role> findByName(RoleName name);
}

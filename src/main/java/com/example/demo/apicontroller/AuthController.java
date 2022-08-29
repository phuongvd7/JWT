package com.example.demo.apicontroller;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.dto.SignInForm;
import com.example.demo.dto.SignUpForm;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.security.UserPrinciple;
import com.example.demo.service.RoleServiceImpl;
import com.example.demo.service.UserServiceImpl;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	RoleServiceImpl roleService;

	@Autowired // muon autorwired thi phai tao bean
	PasswordEncoder passwordEncoder;

	// phuc vu cho dang nhap
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
		if (userService.existsByUsername(signUpForm.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("The username existed! Vui long thu lai"), HttpStatus.OK);
		}
		if (userService.existsByEmail(signUpForm.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("The email existed! Vui long thu lai"), HttpStatus.OK);
		}

		User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(),
				passwordEncoder.encode(signUpForm.getPassword()));
		Set<String> strRoles = signUpForm.getRoles();
		Set<Role> roles = new HashSet<>();
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleService.findByName(RoleName.ADMIN)
						.orElseThrow(() -> new RuntimeException("Role not found"));
				roles.add(adminRole);
				break;

			case "pm":
				Role pmRole = roleService.findByName(RoleName.PM)
						.orElseThrow(() -> new RuntimeException("Role NOT FOUND"));
				roles.add(pmRole);
				break;
			default:
				Role userRole = roleService.findByName(RoleName.USER)
						.orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));
				roles.add(userRole);
			}
		});
		user.setRoles(roles);
		userService.save(user);
		return new ResponseEntity<>(new ResponseMessage("create success!!!"), HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.createToken(authentication); // day la authentication cua he thong
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getAuthorities()));
	}
}

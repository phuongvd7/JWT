package com.example.demo.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
@Service
public class UserDetailService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	@Override
	@Transactional // lien quan den db dung them tk nay
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // ==> ke thua lai, ham nay tim xem user co ton tai o tren db khong 
		
		User user = userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("khong tim thay user -> username or password" + username));
		
		return UserPrinciple.build(user);
	}

}

package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.demo.dao.User;

@Service
public class MyUserDetailService implements UserDetailsService {
	
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.findByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found"+ username));
		
		return user.map(MyUserDetail::new).get();
	}
	
	public User save(User userDto) {
		User user = new User(userDto.getName(), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
			"ROLE_USER");
		return repo.save(user);
	}
}

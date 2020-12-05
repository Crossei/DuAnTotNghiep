package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NonUniqueResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.dao.User;

import antlr.collections.List;

@Service
public class MyUserDetailService implements UserDetailsService {

	private static final int List = 0;
	@Autowired
	UserRepository repo;
	@Autowired
	User2Repository repo2;
	private User user;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.findByUsername(username);

		user.orElseThrow(() -> new UsernameNotFoundException("Not found" + username));

		return user.map(MyUserDetail::new).get();
	}

	public User save(User userDto) {
		User user = new User(userDto.getName(), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
				"ROLE_USER");
		return repo.save(user);
	}

	public void updateResetPassword(String token, String username) throws UserNotFoundException {

		java.util.List<User> user = repo2.findByUsername(username);
		System.out.println(user);
		if (!user.isEmpty()) {
			for (User pass : user) {
				if (username.equals(pass.getUsername())) {
					pass.setReset_password_token(token);
					repo2.save(pass);
					break;
				}
			}
		} else {
			throw new UserNotFoundException("Khong tim thay email :" + username);
		}

	}

	public User get(String token) {
		return repo2.findByResetPasswordToken(token);
	}

	public void updatePassword(User user, String newPass) {
		String encodedPass = passwordEncoder.encode(newPass);

		user.setPassword(encodedPass);
		user.setReset_password_token(null);

		repo2.save(user);
	}
	public String getName() {
		return user.getName();
	}
	
}

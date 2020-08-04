package com.busticketapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.busticketapp.model.Role;
import com.busticketapp.model.User;
import com.busticketapp.repository.RoleRepository;
import com.busticketapp.repository.UserRepository;
import java.util.HashSet;

import javax.transaction.Transactional;

import java.util.Arrays;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCrypt.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user); 
	}

	@Override
	public void deleteUser(int userId) {
		userRepository.deleteById(userId);
		Role role = new Role();
		role.setId(1);
		role.setRole("ADMIN");
		createRole(role);
	}

	public void createRole(Role role) {
		roleRepository.save(role);
	}
	
}

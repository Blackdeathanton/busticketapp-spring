package com.busticketapp.service;

import com.busticketapp.model.User;

public interface UserService {
	
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void deleteUser(int userId);
}

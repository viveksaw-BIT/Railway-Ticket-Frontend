package com.ortb.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortb.users.repo.UsersRepo;
import com.ortb.users.model.*;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepo usersRepo;
	
	public List<Users> getAllUSers() {
		return usersRepo.findAll();
	}
	
	public Users getUserById(int id) {
		return usersRepo.findById(id).orElseThrow();
	}

	public Users registerNewUser(Users user) {
		return usersRepo.save(user);
	}
	
	public Users loginUser(Users user) {
		return usersRepo.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}

	public Users deleteUser(int id) {
		Users gUser = usersRepo.findById(id).orElseThrow();
		usersRepo.deleteById(id);
		return gUser;
		//return null;
	}

	public Users updateUser(int id, Users user) {
		Users gUser = usersRepo.findById(id).orElseThrow();
		gUser.setUsername(user.getUsername());
		gUser.setPassword(user.getPassword());
		gUser.setUser_fname(user.getUser_fname());
		gUser.setUser_lname(user.getUser_lname());
		return usersRepo.save(gUser);
	}

	public List<Users> getAllUsers() {
		// TODO Auto-generated method stub
		return usersRepo.findAll();
	}
	
	
	
}

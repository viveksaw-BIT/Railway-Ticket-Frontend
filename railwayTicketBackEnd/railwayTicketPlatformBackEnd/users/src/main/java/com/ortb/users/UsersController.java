package com.ortb.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortb.users.model.Users;
import com.ortb.users.service.UsersService;

@RestController
//@RequestMapping("/")
public class UsersController {


	@Autowired
	private UsersService usersService;
	
	@PostMapping("/register")
	public Users registerUser(@RequestBody Users user) {
		return usersService.registerNewUser(user);
	}
	
	@PostMapping("/login")
	public Users loginUser(@RequestBody Users user) {
		return usersService.loginUser(user);
	}
	
	@GetMapping("/users/{id}")
	public Users getUser(@PathVariable int id) {
		return usersService.getUserById(id);
		//return "Hi";
	}
	
	@GetMapping("/users")
	public List<Users> getAllUser() {
		return usersService.getAllUsers();
	}
	
	
	@PutMapping("/users/{id}")
	public Users updateUser(@PathVariable int id, @RequestBody Users user) {
		return usersService.updateUser(id,user);
	}
	
	@DeleteMapping("/users/{id}")
	public Users deleteUser(@PathVariable int id) {
		return usersService.deleteUser(id);
	}
	
//	@GetMapping("/fetch")
//	public String fetch() {
//		return "Fetching User";
//	}
}

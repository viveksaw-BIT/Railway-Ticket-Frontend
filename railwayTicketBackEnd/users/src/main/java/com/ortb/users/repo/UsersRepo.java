package com.ortb.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.users.model.Users;

public interface UsersRepo extends JpaRepository<Users, Integer> {

	Users findByUsernameAndPassword(String username, String password);

}

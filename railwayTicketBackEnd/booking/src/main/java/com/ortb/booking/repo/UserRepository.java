package com.ortb.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.booking.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
}
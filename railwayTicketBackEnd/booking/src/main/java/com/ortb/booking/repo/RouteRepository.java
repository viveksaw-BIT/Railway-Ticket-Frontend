package com.ortb.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.booking.model.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
	
}

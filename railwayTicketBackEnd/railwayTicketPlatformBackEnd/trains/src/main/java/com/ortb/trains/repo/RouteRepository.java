package com.ortb.trains.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.trains.model.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
	
}

package com.ortb.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.booking.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
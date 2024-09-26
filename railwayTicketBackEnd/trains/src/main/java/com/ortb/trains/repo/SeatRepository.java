package com.ortb.trains.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.trains.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
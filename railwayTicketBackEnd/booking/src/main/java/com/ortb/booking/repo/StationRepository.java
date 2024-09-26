package com.ortb.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.booking.model.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

}

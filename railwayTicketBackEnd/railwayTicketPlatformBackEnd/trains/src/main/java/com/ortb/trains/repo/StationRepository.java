package com.ortb.trains.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.trains.model.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

}

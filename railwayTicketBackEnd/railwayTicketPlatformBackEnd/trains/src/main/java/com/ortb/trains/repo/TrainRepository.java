package com.ortb.trains.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.trains.model.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

}

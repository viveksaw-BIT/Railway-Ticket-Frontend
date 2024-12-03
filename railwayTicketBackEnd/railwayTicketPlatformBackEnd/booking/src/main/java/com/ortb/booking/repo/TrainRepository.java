package com.ortb.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.booking.model.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

}

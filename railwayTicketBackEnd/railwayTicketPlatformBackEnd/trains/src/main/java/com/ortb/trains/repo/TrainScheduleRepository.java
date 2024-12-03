package com.ortb.trains.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.trains.model.TrainSchedule;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
	List<TrainSchedule> findByTrainId(Long trainId);
}

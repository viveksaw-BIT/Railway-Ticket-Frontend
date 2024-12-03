package com.ortb.booking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.booking.model.TrainSchedule;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
	List<TrainSchedule> findByTrainId(Long trainId);
}

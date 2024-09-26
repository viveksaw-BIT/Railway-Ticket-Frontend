package com.ortb.search.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortb.search.model.TrainSchedule;

@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
    TrainSchedule findByTrainIdAndDate(Long trainId, LocalDate date);
}
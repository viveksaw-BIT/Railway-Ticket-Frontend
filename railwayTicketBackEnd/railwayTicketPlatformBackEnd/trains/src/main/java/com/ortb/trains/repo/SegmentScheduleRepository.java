package com.ortb.trains.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ortb.trains.model.SegmentSchedule;

public interface SegmentScheduleRepository extends JpaRepository<SegmentSchedule, Long> {
	List<SegmentSchedule> findBySegmentId(Long segmentId);
	List<SegmentSchedule> findByTrainScheduleId(Long trainScheduleId);
	//boolean existsByTrainIdAndBookedSeatsGreaterThan(Long id, int i);
	@Query("SELECT COUNT(ss) > 0 FROM SegmentSchedule ss WHERE ss.trainSchedule.train.id = :trainId AND ss.bookedSeats > :bookedSeats")
    boolean existsByTrainIdAndBookedSeatsGreaterThan(@Param("trainId") Long trainId, @Param("bookedSeats") int bookedSeats);
}
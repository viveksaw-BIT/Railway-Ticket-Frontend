package com.ortb.trains.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.trains.model.Segment;

public interface SegmentRepository extends JpaRepository<Segment, Long>{

	Optional<Segment> findByStartStationIdAndEndStationId(Long startStationId, Long endStationId);
}

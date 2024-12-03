package com.ortb.booking.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ortb.booking.dto.SegmentBookedSeatsDTO;
import com.ortb.booking.model.Segment;

public interface SegmentRepository extends JpaRepository<Segment, Long>{

	//List<Segment> findByStartStationIdAndEndStationId(Long startStationId, Long endStationId);
//	@Query("SELECT s FROM Segment s WHERE s.startStationId <= :sourceStationId AND s.endStationId >= :destinationStationId")
//	List<Segment> findByStartStationIdAndEndStationId(@Param("sourceStationId") Long sourceStationId, @Param("destinationStationId") Long destinationStationId);
	@Query("SELECT s FROM Segment s WHERE s.startStationId < :endStationId AND s.endStationId > :startStationId ORDER BY s.startStationId")
    List<Segment> findByStartStationIdAndEndStationId(@Param("startStationId") Long startStationId, @Param("endStationId") Long endStationId);
	
	@Query("SELECT s FROM Segment s WHERE s.startStationId < :endStationId AND s.endStationId > :startStationId AND s.train.id = :trainId ORDER BY s.startStationId")
    List<Segment> findByTrainIdAndStartStationIdAndEndStationId(@Param("trainId") Long trainId,@Param("startStationId") Long startStationId, @Param("endStationId") Long endStationId);
	
//	@Query("SELECT s FROM Segment s " +
//		       "JOIN Station startStation ON s.startStationId = startStation.id " +
//		       "JOIN Station endStation ON s.endStationId = endStation.id " +
//		       "WHERE startStation.name = :startStationName " +
//		       "AND endStation.name = :endStationName " +
//		       "AND s.train.id = :trainId " +
//		       "ORDER BY s.startStationId")
//		List<Segment> findByTrainIdAndStartStationNameAndEndStationName(
//		    @Param("trainId") Long trainId,
//		    @Param("startStationName") String startStationName,
//		    @Param("endStationName") String endStationName
//		);
	
	@Query("SELECT s FROM Segment s " +
	           "JOIN Station startStation ON s.startStationId = startStation.id " +
	           "JOIN Station endStation ON s.endStationId = endStation.id " +
	           "WHERE startStation.id >= (SELECT id FROM Station WHERE name = :startStationName) " +
	           "AND endStation.id <= (SELECT id FROM Station WHERE name = :endStationName) " +
	           "AND s.train.id = :trainId " +
	           "ORDER BY s.startStationId")
	    List<Segment> findByTrainIdAndStartStationNameAndEndStationName(
	        @Param("trainId") Long trainId,
	        @Param("startStationName") String startStationName,
	        @Param("endStationName") String endStationName
	    );
	
//	@Query("SELECT new com.ortb.booking.model.SegmentBookedSeatsDTO(s.id, ss.bookedSeats) " +
//		       "FROM Segment s " +
//		       "JOIN SegmentSchedule ss ON ss.segment.id = s.id " +
//		       "WHERE s.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
//		       "AND s.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
//		       "AND s.train.id = :trainId " +
//		       "ORDER BY s.startStationId")
//		List<SegmentBookedSeatsDTO> findSegmentsWithBookedSeats(
//		    @Param("trainId") Long trainId,
//		    @Param("startStationName") String startStationName,
//		    @Param("endStationName") String endStationName
//		);
//	@Query("SELECT new com.ortb.booking.model.SegmentBookedSeatsDTO(s.id, s.totalSeats, ss.bookedSeats) " +
//		       "FROM Segment s " +
//		       "JOIN SegmentSchedule ss ON ss.segment.id = s.id " +
//		       "WHERE s.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
//		       "AND s.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
//		       "AND s.train.id = :trainId " +
//		       "ORDER BY s.startStationId")
//		List<SegmentBookedSeatsDTO> findSegmentsWithBookedSeats(
//		    @Param("trainId") Long trainId,
//		    @Param("startStationName") String startStationName,
//		    @Param("endStationName") String endStationName
//		);
	@Query("SELECT new com.ortb.booking.dto.SegmentBookedSeatsDTO(s.id, s.totalSeats, ss.bookedSeats) " +
		       "FROM Segment s JOIN SegmentSchedule ss ON ss.segment.id = s.id " +
		       "WHERE s.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
		       "AND s.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
		       "AND s.train.id = :trainId " +
		       "ORDER BY s.startStationId")
		List<SegmentBookedSeatsDTO> findSegmentsWithBookedSeats(@Param("trainId") Long trainId,
		                                                        @Param("startStationName") String startStationName,
		                                                        @Param("endStationName") String endStationName);
	
	//--------------------for getting segment ids between the provided stations
	@Query("SELECT s.id FROM Segment s WHERE s.train.id = :trainId " +
		       "AND s.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
		       "AND s.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
		       "ORDER BY s.startStationId")
		List<Long> findSegmentIdsBetweenStations(@Param("trainId") Long trainId,
		                                         @Param("startStationName") String startStationName,
		                                         @Param("endStationName") String endStationName);



}

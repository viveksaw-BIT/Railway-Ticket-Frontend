package com.ortb.booking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ortb.booking.dto.SeatDTO;
import com.ortb.booking.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
//	// Fetch count of seats booked per segment based on quota
//    @Query("SELECT s.quota, COUNT(s) FROM Seat s WHERE s.segment.id = :segmentId GROUP BY s.quota")
//    List<Object[]> countSeatsByQuota(@Param("segmentId") Long segmentId);
//
//    // Fetch count of seats booked per segment based on seatClass
//    @Query("SELECT s.seatClass, COUNT(s) FROM Seat s WHERE s.segment.id = :segmentId GROUP BY s.seatClass")
//    List<Object[]> countSeatsBySeatClass(@Param("segmentId") Long segmentId);
//
//    // Fetch count of seats booked per segment based on both seatClass and quota
//    @Query("SELECT s.seatClass, s.quota, COUNT(s) FROM Seat s WHERE s.segment.id = :segmentId GROUP BY s.seatClass, s.quota")
//    List<Object[]> countSeatsBySeatClassAndQuota(@Param("segmentId") Long segmentId);
	// Fetch count of seats booked per segment based on quota
//    @Query("SELECT s.quota, COUNT(s) FROM Seat s WHERE s.segment.id = :segmentId GROUP BY s.quota")
//    List<Object[]> countSeatsByQuota(@Param("segmentId") Long segmentId);
//
//    // Fetch count of seats booked per segment based on seatClass
//    @Query("SELECT s.seatClass, COUNT(s) FROM Seat s WHERE s.segment.id = :segmentId GROUP BY s.seatClass")
//    List<Object[]> countSeatsBySeatClass(@Param("segmentId") Long segmentId);

    // Fetch count of seats booked per segment based on both seatClass and quota
    @Query("SELECT new com.ortb.booking.dto.SeatDTO(s.seatClass, s.quota, COUNT(s)) FROM Seat s WHERE s.segment.id = :segmentId GROUP BY s.seatClass, s.quota")
    List<SeatDTO> countSeatsBySeatClassAndQuota(@Param("segmentId") Long segmentId);
    
//    @Query("SELECT new com.ortb.booking.dto.SeatDTO(s.seatClass, s.quota, COUNT(s)) " +
//            "FROM Seat s " +
//            "WHERE s.segment.id = :segmentId AND s.seatClass = :seatClass AND s.quota = :quota AND s.seatStatus = 'reserved' " +
//            "GROUP BY s.seatClass, s.quota")
//     List<SeatDTO> countReservedSeatsBySegmentSeatClassAndQuota(@Param("segmentId") Long segmentId,
//                                                                @Param("seatClass") String seatClass,
//                                                                @Param("quota") String quota);
    
    @Query("SELECT new com.ortb.booking.dto.SeatDTO(s.seatClass, s.quota, COUNT(s)) " +
            "FROM Seat s " +
            "WHERE s.segment.id = :segmentId AND s.seatClass = :seatClass AND s.quota = :quota AND s.seatStatus = :status " +
            "GROUP BY s.seatClass, s.quota")
    SeatDTO countSeatsBySegmentSeatClassQuotaAndStatus(@Param("segmentId") Long segmentId,
                                                                @Param("seatClass") String seatClass,
                                                                @Param("quota") String quota,
                                                                @Param("status") String status);
    
    //----------for getting available seats between provided segments--------
    @Query("SELECT s.seatNumber FROM Seat s WHERE s.segment.id IN :segmentIds " +
    	       "AND s.seatStatus = 'Available' " +
    	       "GROUP BY s.seatNumber " +
    	       "HAVING COUNT(s.seatNumber) = :segmentCount")
    	List<Integer> findAvailableSeatNumbersAcrossSegments(@Param("segmentIds") List<Long> segmentIds,
    	                                                     @Param("segmentCount") long segmentCount);
    
    //---------------
//    @Query("SELECT s.seatNumber " +
//            "FROM Seat s " +
//            "WHERE s.segment.id IN (" +
//            "    SELECT seg.id " +
//            "    FROM Segment seg " +
//            "    WHERE seg.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
//            "    AND seg.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
//            "    AND seg.train.id = :trainId) " +
//            "AND s.seatClass = :seatClass " +
//            "AND s.quota = :quota " +
//            "AND s.seatStatus = 'available' " +
//            "GROUP BY s.seatNumber " +
//            "HAVING COUNT(s.seatNumber) = (" +
//            "    SELECT COUNT(seg.id) " +
//            "    FROM Segment seg " +
//            "    WHERE seg.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
//            "    AND seg.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
//            "    AND seg.train.id = :trainId)")
//     List<Integer> findAvailableSeatsAcrossAllSegments(@Param("trainId") Long trainId,
//                                                       @Param("startStationName") String startStationName,
//                                                       @Param("endStationName") String endStationName,
//                                                       @Param("seatClass") String seatClass,
//                                                       @Param("quota") String quota);
    @Query("SELECT s.seatNumber " +
            "FROM Seat s " +
            "WHERE s.segment.id IN (" +
            "    SELECT seg.id " +
            "    FROM Segment seg " +
            "    WHERE seg.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
            "    AND seg.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
            "    AND seg.train.id = :trainId) " +
            "AND s.seatClass = :seatClass " +
            "AND s.quota = :quota " +
            "AND s.seatStatus = :status " +
            "GROUP BY s.seatNumber " +
            "HAVING COUNT(s.seatNumber) = (" +
            "    SELECT COUNT(seg.id) " +
            "    FROM Segment seg " +
            "    WHERE seg.startStationId >= (SELECT id FROM Station WHERE name = :startStationName) " +
            "    AND seg.endStationId <= (SELECT id FROM Station WHERE name = :endStationName) " +
            "    AND seg.train.id = :trainId)")
     List<Integer> findSeatsByStatusSeatClassAndQuotaAcrossAllSegments(@Param("trainId") Long trainId,
                                                      @Param("startStationName") String startStationName,
                                                      @Param("endStationName") String endStationName,
                                                      @Param("seatClass") String seatClass,
                                                      @Param("quota") String quota,
                                                      @Param("status") String status);
    
//    @Transactional
//    @Modifying
//    @Query("UPDATE Seat s " +
//           "SET s.seatStatus = 'Reserved' " +
//           "WHERE s.seatNumber IN :seatNumbers " +
//           "AND s.segment.id IN :segmentIds")
//    void updateSeatStatusToReserved(@Param("seatNumbers") List<Integer> seatNumbers, 
//                                    @Param("segmentIds") List<Long> segmentIds);
    
    @Transactional
    @Modifying
    @Query("UPDATE Seat s " +
           "SET s.seatStatus = :status " +
           "WHERE s.seatNumber IN :seatNumbers " +
           "AND s.segment.id IN :segmentIds")
    void updateSeatStatus(@Param("seatNumbers") List<Integer> seatNumbers, 
                          @Param("segmentIds") List<Long> segmentIds,
                          @Param("status") String status);


}
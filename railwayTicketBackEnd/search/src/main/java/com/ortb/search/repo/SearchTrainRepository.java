package com.ortb.search.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ortb.search.model.Train;

@Repository
public interface SearchTrainRepository extends JpaRepository<Train, Long> {
    List<Train> findByName(String name);

    @Query("SELECT t FROM Train t JOIN t.route r JOIN r.stations s WHERE s.id = :startStationId")
    List<Train> findByStartStation(@Param("startStationId") Long startStationId);

    @Query("SELECT t FROM Train t JOIN t.route r JOIN r.stations s WHERE s.id = :endStationId")
    List<Train> findByEndStation(@Param("endStationId") Long endStationId);

    @Query("SELECT t FROM Train t JOIN t.route r JOIN r.stations sStart JOIN r.stations sEnd " +
           "WHERE sStart.id = :startStationId AND sEnd.id = :endStationId")
    List<Train> findByStartAndEndStations(@Param("startStationId") Long startStationId, @Param("endStationId") Long endStationId);

    @Query("SELECT t FROM Train t JOIN t.trainSchedules ts " +
           "JOIN t.route r JOIN r.stations sStart JOIN r.stations sEnd " +
           "WHERE sStart.id = :startStationId AND sEnd.id = :endStationId AND ts.date = :date")
    List<Train> findByStartAndEndStationsAndDate(@Param("startStationId") Long startStationId, 
                                                 @Param("endStationId") Long endStationId,
                                                 @Param("date") LocalDate date);
    
//    @Query("SELECT t FROM Train t JOIN t.trainSchedules ts " +
//    	       "JOIN t.route r JOIN r.stations sStart JOIN r.stations sEnd " +
//    	       "WHERE sStart.name = :startStationName AND sEnd.name = :endStationName AND ts.date = :date")
//    List<Train> findByStartAndEndStationsNameAndDate(@Param("startStationName") String startStationName, 
//    	                                             @Param("endStationName") String endStationName,
//    	                                             @Param("date") LocalDate date);
    
//    @Query("SELECT t FROM Train t " +
//    	       "JOIN t.trainSchedules ts " +
//    	       "JOIN t.route r " +
//    	       "JOIN r.segments segStart " +
//    	       "JOIN r.segments segEnd " +
//    	       "WHERE segStart.startStation.name = :startStationName " +
//    	       "AND segEnd.endStation.name = :endStationName " +
//    	       "AND segStart.segmentOrder < segEnd.segmentOrder " +
//    	       "AND ts.date = :date")
//    	List<Train> findByStartAndEndStationsNameAndDate(@Param("startStationName") String startStationName, 
//    	                                                       @Param("endStationName") String endStationName,
//    	                                                       @Param("date") LocalDate date);
    
//    @Query("SELECT DISTINCT t FROM Train t JOIN t.route r " +
//    	       "JOIN r.stations sStart JOIN r.stations sEnd " +
//    	       "JOIN t.trainSchedules ts " +
//    	       "WHERE sStart.name = :startStationName " +
//    	       "AND sEnd.name = :endStationName " +
//    	       "AND ts.date = :date " +
//    	       "AND (SELECT idx FROM RouteStation rs " +
//    	            "WHERE rs.route.id = r.id AND rs.station.id = sStart.id) " +
//    	       "<= (SELECT idx FROM RouteStation rs " +
//    	            "WHERE rs.route.id = r.id AND rs.station.id = sEnd.id)")
//    	List<Train> findByStartAndEndStationsNameAndDate(@Param("startStationName") String startStationName, 
//    	                                             @Param("endStationName") String endStationName,
//    	                                             @Param("date") LocalDate date);
//    @Query("SELECT t FROM Train t JOIN t.route r JOIN r.stations sStart, r.stations sEnd " +
//    	       "WHERE sStart.name = :startStationName AND sEnd.name = :endStationName " +
//    	       "AND sStart.stationOrder < sEnd.stationOrder AND EXISTS (" +
//    	       "  SELECT 1 FROM t.trainSchedules ts WHERE ts.date = :date" +
//    	       ")")
//    	List<Train> findByStartAndEndStationsNameAndDate(@Param("startStationName") String startStationName,
//    	                                                 @Param("endStationName") String endStationName,
//    	                                                 @Param("date") LocalDate date);
    
    @Query("SELECT DISTINCT t FROM Train t " +
    	       "JOIN t.route r " +
    	       "JOIN r.stations sStart " +
    	       "JOIN r.stations sEnd " +
    	       "JOIN t.trainSchedules ts " +
    	       "WHERE sStart.name = :startStationName " +
    	       "AND sEnd.name = :endStationName " +
    	       "AND ts.date = :date")
    	List<Train> findByStartAndEndStationsNameAndDate(@Param("startStationName") String startStationName,
    	                                             @Param("endStationName") String endStationName,
    	                                             @Param("date") LocalDate date);





}
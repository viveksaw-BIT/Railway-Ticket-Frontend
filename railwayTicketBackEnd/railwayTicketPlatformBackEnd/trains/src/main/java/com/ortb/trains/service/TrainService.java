package com.ortb.trains.service;
import com.ortb.trains.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortb.trains.model.Train;
import com.ortb.trains.model.TrainSchedule;
import com.ortb.trains.model.Route;
import com.ortb.trains.model.Seat;
import com.ortb.trains.model.Segment;
import com.ortb.trains.model.SegmentSchedule;
import com.ortb.trains.model.Station;
import com.ortb.trains.repo.RouteRepository;
import com.ortb.trains.repo.SeatRepository;
import com.ortb.trains.repo.SegmentRepository;
import com.ortb.trains.repo.SegmentScheduleRepository;
import com.ortb.trains.repo.TrainRepository;
import com.ortb.trains.repo.TrainScheduleRepository;

import jakarta.transaction.Transactional;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private TrainScheduleRepository trainScheduleRepository;
    
    @Autowired
    private SegmentScheduleRepository segmentScheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    public Train createTrain(TrainDTO trainDTO) {
        // Fetch the route
        Route route = routeRepository.findById(trainDTO.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        // Create Train
        Train train = new Train();
        train.setName(trainDTO.getName());
        train.setNumber(trainDTO.getNumber());
        train.setRoute(route);

        // Save Train to generate its ID
        train = trainRepository.save(train);

        // Create and Save Segments based on the Route's Stations
        List<Station> stations = route.getStations();
        if (stations == null || stations.contains(null)) {
            throw new RuntimeException("Stations list is null or contains null elements");
        }

        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < stations.size() - 1; i++) {
            Segment segment = new Segment();
            segment.setStartStationId(stations.get(i).getId());
            segment.setEndStationId(stations.get(i + 1).getId());
            segment.setTotalSeats(trainDTO.getTotalSeats());
            segment.setTrain(train);
            segments.add(segment);
        }
        train.setSegments(segments);

        // Create and Save TrainSchedule
        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setTrain(train);
        trainSchedule.setDate(trainDTO.getScheduleDate()); // Dynamic date
        trainSchedule.setDepartureTime(trainDTO.getDepartureTime()); // Dynamic departure time
        trainSchedule.setArrivalTime(trainDTO.getArrivalTime()); // Dynamic arrival time

        // Save TrainSchedule to generate its ID
        List<TrainSchedule> trainSchedules = new ArrayList<>();
        trainSchedules.add(trainSchedule);
        trainSchedule = trainScheduleRepository.save(trainSchedule);

        // Create SegmentSchedules and associate with segments and trainSchedule
        for (Segment segment : segments) {
            SegmentSchedule segmentSchedule = new SegmentSchedule();
            segmentSchedule.setSegment(segment);
            segmentSchedule.setTrainSchedule(trainSchedule);
            segmentSchedule.setBookedSeats(0); // or any default value

            segment.getSegmentSchedules().add(segmentSchedule);
            segmentScheduleRepository.save(segmentSchedule);
        }

        // Create and save Seats for each segment
        for (Segment segment : segments) {
            createSeatsForSegment(segment, trainDTO.getTotalSeats());
        }

        // Save the Train with all its segments and schedules
        return trainRepository.save(train);
    }

    private void createSeatsForSegment(Segment segment, int totalSeats) {
        int acSeats = (int) (totalSeats * 0.4);
        int nonAcSeats = totalSeats - acSeats;

        int acLadiesSeats = (int) (acSeats * 0.3);
        int acGeneralSeats = acSeats - acLadiesSeats;

        int nonAcLadiesSeats = (int) (nonAcSeats * 0.3);
        int nonAcGeneralSeats = nonAcSeats - nonAcLadiesSeats;

        List<Seat> seats = new ArrayList<>();

        // A/C Ladies Quota
        for (int i = 1; i <= acLadiesSeats; i++) {
            Seat seat = new Seat();
            seat.setSegment(segment);
            seat.setSeatClass("A/C");
            seat.setQuota("Ladies Quota");
          //Update for Status of Seat, need to update in other microservices as well
            seat.setSeatStatus("Available");
            seat.setSeatNumber(i);
            seats.add(seat);
        }

        // A/C General Quota
        for (int i = acLadiesSeats + 1; i <= acLadiesSeats + acGeneralSeats; i++) {
            Seat seat = new Seat();
            seat.setSegment(segment);
            seat.setSeatClass("A/C");
            seat.setQuota("General Quota");
          //Update for Status of Seat, need to update in other microservices as well
            seat.setSeatStatus("Available");
            seat.setSeatNumber(i);
            seats.add(seat);
        }

        // Non-A/C Ladies Quota
        for (int i = 1; i <= nonAcLadiesSeats; i++) {
            Seat seat = new Seat();
            seat.setSegment(segment);
            seat.setSeatClass("Non-A/C");
            seat.setQuota("Ladies Quota");
          //Update for Status of Seat, need to update in other microservices as well
            seat.setSeatStatus("Available");
            seat.setSeatNumber(acSeats + i);
            seats.add(seat);
        }

        // Non-A/C General Quota
        for (int i = nonAcLadiesSeats + 1; i <= nonAcLadiesSeats + nonAcGeneralSeats; i++) {
            Seat seat = new Seat();
            seat.setSegment(segment);
            seat.setSeatClass("Non-A/C");
            seat.setQuota("General Quota");
          //Update for Status of Seat, need to update in other microservices as well
            seat.setSeatStatus("Available");
            seat.setSeatNumber(acSeats + i);
            seats.add(seat);
        }

        seatRepository.saveAll(seats);
    }
    
    @Transactional
    public Train updateTrain(Long id, TrainDTO trainDTO) {
        // Fetch existing train
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        // Fetch the route
        Route route = routeRepository.findById(trainDTO.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        
        // Check for booked tickets
        boolean hasBookedTickets = segmentScheduleRepository.existsByTrainIdAndBookedSeatsGreaterThan(id, 0);
        if (hasBookedTickets) {
            throw new RuntimeException("Cannot update train details as there are booked tickets.");
        }

        // Update Train details
        train.setName(trainDTO.getName());
        train.setNumber(trainDTO.getNumber());
        train.setRoute(route);

        // Fetch existing schedules
        List<TrainSchedule> existingSchedules = trainScheduleRepository.findByTrainId(id);

        // Handle existing schedules and segment schedules
        if (!existingSchedules.isEmpty()) {
            List<SegmentSchedule> segmentSchedulesToDelete = existingSchedules.stream()
                    .flatMap(schedule -> schedule.getSegmentSchedules().stream())
                    .collect(Collectors.toList());

            segmentScheduleRepository.deleteAll(segmentSchedulesToDelete);
            trainScheduleRepository.deleteAll(existingSchedules);
        }

        // Fetch existing segments
        List<Segment> existingSegments = new ArrayList<>(train.getSegments());

        // Clear the segments from the train to avoid orphan entities
        train.getSegments().clear();
        segmentRepository.deleteAll(existingSegments);

        // Create and add new segments
        List<Station> stations = route.getStations();
        List<Segment> newSegments = new ArrayList<>();
        for (int i = 0; i < stations.size() - 1; i++) {
            Segment segment = new Segment();
            segment.setStartStationId(stations.get(i).getId());
            segment.setEndStationId(stations.get(i + 1).getId());
            segment.setTotalSeats(trainDTO.getTotalSeats());
            segment.setTrain(train);  // Ensure train reference is set correctly
            newSegments.add(segment);
        }

        // Save the new segments
        newSegments = segmentRepository.saveAll(newSegments);
        train.getSegments().addAll(newSegments);  // Set new segments

        // Create new TrainSchedule and SegmentSchedules
        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setTrain(train);
        trainSchedule.setDate(trainDTO.getScheduleDate());
        trainSchedule.setDepartureTime(trainDTO.getDepartureTime());
        trainSchedule.setArrivalTime(trainDTO.getArrivalTime());
        trainSchedule = trainScheduleRepository.save(trainSchedule);

        for (Segment segment : newSegments) {
            SegmentSchedule segmentSchedule = new SegmentSchedule();
            segmentSchedule.setSegment(segment);
            segmentSchedule.setTrainSchedule(trainSchedule);
            segmentSchedule.setBookedSeats(0); // or any default value
            segmentScheduleRepository.save(segmentSchedule);
        }

        // Create and save Seats for each new segment
        for (Segment segment : newSegments) {
            createSeatsForSegment(segment, trainDTO.getTotalSeats());
        }

        // Save the train entity which will handle new segments and schedules
        return trainRepository.save(train);
    }

//------------------------------------------------------------------------------------
//    private void createSeatsForSegment(Segment segment, int totalSeats) {
//        // Your logic to create seats for a segment
//        // For example:
//        int acSeats = (int) (totalSeats * 0.4);
//        int nonAcSeats = totalSeats - acSeats;
//
//        int ladiesAcSeats = (int) (acSeats * 0.3);
//        int generalAcSeats = acSeats - ladiesAcSeats;
//
//        int ladiesNonAcSeats = (int) (nonAcSeats * 0.3);
//        int generalNonAcSeats = nonAcSeats - ladiesNonAcSeats;
//
//        List<Seat> seats = new ArrayList<>();
//        for (int i = 1; i <= totalSeats; i++) {
//            Seat seat = new Seat();
//            seat.setSegment(segment);
//            if (i <= acSeats) {
//                seat.setSeatClass(SeatClass.AC);
//                if (i <= ladiesAcSeats) {
//                    seat.setQuota(Quota.LADIES);
//                } else {
//                    seat.setQuota(Quota.GENERAL);
//                }
//            } else {
//                seat.setSeatClass(SeatClass.NON_AC);
//                if (i <= acSeats + ladiesNonAcSeats) {
//                    seat.setQuota(Quota.LADIES);
//                } else {
//                    seat.setQuota(Quota.GENERAL);
//                }
//            }
//            seats.add(seat);
//        }
//
//        seatRepository.saveAll(seats);
//    }
//----------------------------------------------------------------------------


    public void deleteTrain(Long id) {
    	Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        
        // Check for booked tickets
        boolean hasBookedTickets = segmentScheduleRepository.existsByTrainIdAndBookedSeatsGreaterThan(id, 0);
        if (hasBookedTickets) {
            throw new RuntimeException("Cannot delete train details as there are booked tickets.");
        }
        trainRepository.deleteById(id);
    }

    public Train getTrainById(Long id) {
        return trainRepository.findById(id).orElse(null);
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }
}
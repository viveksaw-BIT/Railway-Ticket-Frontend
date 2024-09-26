package com.ortb.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortb.booking.model.TrainSchedule;
import com.ortb.booking.repo.TrainScheduleRepository;

//@Service
//public class TrainScheduleService {
//
//    @Autowired
//    private TrainScheduleRepository trainScheduleRepository;
//
//    public TrainSchedule updateTrainSchedule(Long id, TrainSchedule updatedSchedule) {
//        TrainSchedule existingSchedule = trainScheduleRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("TrainSchedule not found with id " + id));
//        existingSchedule.setSeatsAvailable(updatedSchedule.getSeatsAvailable());
//        existingSchedule.setTotalSeats(updatedSchedule.getTotalSeats());
//        return trainScheduleRepository.save(existingSchedule);
//    }
//}
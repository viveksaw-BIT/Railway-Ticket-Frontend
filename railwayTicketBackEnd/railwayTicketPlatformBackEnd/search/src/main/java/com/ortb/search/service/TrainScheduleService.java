package com.ortb.search.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortb.search.model.TrainSchedule;
import com.ortb.search.repo.TrainScheduleRepository;

@Service
public class TrainScheduleService {

    @Autowired
    private TrainScheduleRepository trainScheduleRepository;

    public TrainSchedule getTrainSchedule(Long trainId, LocalDate date) {
        return trainScheduleRepository.findByTrainIdAndDate(trainId, date);
    }
}
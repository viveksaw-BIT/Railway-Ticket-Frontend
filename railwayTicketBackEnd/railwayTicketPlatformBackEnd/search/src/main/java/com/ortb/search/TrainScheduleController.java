package com.ortb.search;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortb.search.model.TrainSchedule;
import com.ortb.search.service.TrainScheduleService;

@RestController
@RequestMapping("/trains")
public class TrainScheduleController {

    @Autowired
    private TrainScheduleService trainScheduleService;

    @GetMapping("/schedule")
    public ResponseEntity<TrainSchedule> getTrainSchedule(
            @RequestParam Long trainId,
            @RequestParam LocalDate date) {
    	TrainSchedule schedules = trainScheduleService.getTrainSchedule(trainId, date);
        return ResponseEntity.ok(schedules);
    }
}
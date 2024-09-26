package com.ortb.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortb.booking.dto.TrainDTO;
import com.ortb.booking.model.Train;
import com.ortb.booking.service.TrainService;

@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping
    public ResponseEntity<Train> createTrain(@RequestBody TrainDTO trainDTO) {
        Train createdTrain = trainService.createTrain(trainDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrain);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Train> updateTrain(@PathVariable Long id, @RequestBody TrainDTO trainDTO) {
        Train updatedTrain = trainService.updateTrain(id, trainDTO);
        return ResponseEntity.ok(updatedTrain);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable Long id) {
        Train train = trainService.getTrainById(id);
        return ResponseEntity.ok(train);
    }

    @GetMapping
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = trainService.getAllTrains();
        return ResponseEntity.ok(trains);
    }
    
    @PutMapping("/update/seat/{sourceStationId}/{destinationStationId}/{bookedSeats}")
    public ResponseEntity<Void> updateBookedSeat(@PathVariable Long sourceStationId, @PathVariable Long destinationStationId, @PathVariable int bookedSeats){
    	trainService.updateBookedSeatsBetweenStations(sourceStationId,destinationStationId,bookedSeats);
    	return ResponseEntity.noContent().build();
    }
    
//    @PutMapping("/update/seat/{trainId}/{sourceStationId}/{destinationStationId}/{bookedSeats}")
//    public ResponseEntity<Void> updateBookedSeatByTrainId(@PathVariable Long trainId,@PathVariable Long sourceStationId, @PathVariable Long destinationStationId, @PathVariable int bookedSeats){
//    	trainService.updateBookedSeatsBetweenStationsByTrainId(trainId,sourceStationId,destinationStationId,bookedSeats);
//    	return ResponseEntity.noContent().build();
//    }
    
    @PutMapping("/update/seat/{trainId}/{startStationName}/{endStationName}/{bookedSeats}")
    public ResponseEntity<Void> updateBookedSeatByTrainId(@PathVariable Long trainId,@PathVariable String startStationName, @PathVariable String endStationName, @PathVariable int bookedSeats){
    	trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(trainId,startStationName,endStationName,bookedSeats);
    	return ResponseEntity.noContent().build();
    }
}
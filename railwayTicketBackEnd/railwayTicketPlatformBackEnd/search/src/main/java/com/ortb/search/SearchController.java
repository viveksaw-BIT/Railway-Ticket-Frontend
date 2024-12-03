package com.ortb.search;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortb.search.model.Train;
import com.ortb.search.service.SearchService;
import com.ortb.search.service.TrainScheduleService;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    
    //----------
    @Autowired
    private TrainScheduleService trainScheduleService;

    @GetMapping("/train/id/{id}")
    public ResponseEntity<List<Train>> searchTrainById(@PathVariable Long id) {
        List<Train> trains = searchService.searchTrainById(id);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/train/name/{name}")
    public ResponseEntity<List<Train>> searchTrainByName(@PathVariable String name) {
        List<Train> trains = searchService.searchTrainByName(name);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/train/source/{startStationId}/destination/{endStationId}")
    public ResponseEntity<List<Train>> searchTrainByStartAndEndStations(@PathVariable Long startStationId,
                                                                        @PathVariable Long endStationId) {
        List<Train> trains = searchService.searchTrainByStartAndEndStations(startStationId, endStationId);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/train/source/{startStationId}/destination/{endStationId}/date/{date}")
    public ResponseEntity<List<Train>> searchTrainByStartAndEndStationsAndDate(@PathVariable Long startStationId,
                                                                               @PathVariable Long endStationId,
                                                                               @PathVariable LocalDate date) {
        List<Train> trains = searchService.searchTrainByStartAndEndStationsAndDate(startStationId, endStationId, date);
        return ResponseEntity.ok(trains);
    }
    
    @GetMapping("/trains/source/{startStationName}/destination/{endStationName}/date/{date}")
    public ResponseEntity<List<Train>> searchTrainByStartAndEndStationsNameAndDate(@PathVariable String startStationName,
                                                                               @PathVariable String endStationName,
                                                                               @PathVariable LocalDate date) {
        List<Train> trains = searchService.searchTrainByStartAndEndStationsNameAndDate(startStationName, endStationName, date);
        return ResponseEntity.ok(trains);
    }
}
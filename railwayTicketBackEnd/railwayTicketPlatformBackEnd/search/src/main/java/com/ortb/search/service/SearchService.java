package com.ortb.search.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortb.search.model.Train;
import com.ortb.search.repo.SearchTrainRepository;

@Service
public class SearchService {

    @Autowired
    private SearchTrainRepository searchTrainRepository;

    public List<Train> searchTrainById(Long id) {
        return List.of(searchTrainRepository.findById(id).orElse(null));
    }

    public List<Train> searchTrainByName(String name) {
        return searchTrainRepository.findByName(name);
    }

    public List<Train> searchTrainByStartAndEndStations(Long startStationId, Long endStationId) {
        return searchTrainRepository.findByStartAndEndStations(startStationId, endStationId);
    }

    public List<Train> searchTrainByStartAndEndStationsAndDate(Long startStationId, Long endStationId, LocalDate date) {
        return searchTrainRepository.findByStartAndEndStationsAndDate(startStationId, endStationId, date);
    }
    
    public List<Train> searchTrainByStartAndEndStationsNameAndDate(String startStationName, String endStationName, LocalDate date) {
        return searchTrainRepository.findByStartAndEndStationsNameAndDate(startStationName, endStationName, date);
    }
}
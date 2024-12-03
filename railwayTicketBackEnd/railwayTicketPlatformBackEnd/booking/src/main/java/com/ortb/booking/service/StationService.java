package com.ortb.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortb.booking.model.Station;
import com.ortb.booking.repo.StationRepository;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public Station saveStation(Station station) {
        return stationRepository.save(station);
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElse(null);
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station updateStation(Long id, Station stationDetails) {
        Station station = stationRepository.findById(id).orElse(null);
        if (station != null) {
            station.setName(stationDetails.getName());
            station.setLocation(stationDetails.getLocation());
            return stationRepository.save(station);
        }
        return null;
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}
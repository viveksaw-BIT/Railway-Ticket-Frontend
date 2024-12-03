package com.ortb.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortb.booking.model.Route;
import com.ortb.booking.model.Station;
import com.ortb.booking.repo.RouteRepository;
import com.ortb.booking.repo.StationRepository;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StationRepository stationRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    public Route createRoute(Route route) {
        // Ensure all stations are saved
        for (Station station : route.getStations()) {
            if (station.getId() == null || !stationRepository.existsById(station.getId())) {
                stationRepository.save(station);
            }
        }
        return routeRepository.save(route);
    }

    public Route updateRoute(Long id, Route routeDetails) {
        Route route = routeRepository.findById(id).orElse(null);
        if (route != null) {
            route.setName(routeDetails.getName());
            route.setStations(routeDetails.getStations());
            // Ensure all stations are saved
            for (Station station : route.getStations()) {
                if (station.getId() == null || !stationRepository.existsById(station.getId())) {
                    stationRepository.save(station);
                }
            }
            return routeRepository.save(route);
        }
        return null;
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }
}
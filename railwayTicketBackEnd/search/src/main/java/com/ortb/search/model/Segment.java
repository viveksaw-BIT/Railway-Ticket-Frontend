package com.ortb.search.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long startStationId;
    private Long endStationId;
    private int totalSeats;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonBackReference
    private Train train;

    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SegmentSchedule> segmentSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Seat> seats = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(Long startStationId) {
		this.startStationId = startStationId;
	}

	public Long getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(Long endStationId) {
		this.endStationId = endStationId;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public List<SegmentSchedule> getSegmentSchedules() {
		return segmentSchedules;
	}

	public void setSegmentSchedules(List<SegmentSchedule> segmentSchedules) {
		this.segmentSchedules = segmentSchedules;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

    // Getters and setters
    
}
package com.ortb.booking.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnr;
    //private Long trainId;
    //private String trainName;
    //private String journeyDate;
    private LocalDate journeyDate;
    private Double totalFare;
    private String bookingStatus;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    //@JsonManagedReference
    @JsonManagedReference(value = "booking-passenger")
    private List<Passenger> passengers;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    //@JsonManagedReference
    @JsonManagedReference(value = "booking-payment")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@JsonBackReference
    @JsonBackReference(value = "user-booking")
    private Users user;
    
    @ManyToOne
    @JoinColumn(name = "train_id")
    //@JsonBackReference
    //@JsonBackReference(value = "train-booking")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "bookings", "route", "segments", "trainSchedules"}) // Adjust fields as needed
    private Train train;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

//    public Long getTrainId() {
//        return trainId;
//    }
//
//    public void setTrainId(Long trainId) {
//        this.trainId = trainId;
//    }

//    public String getTrainName() {
//        return trainName;
//    }
//
//    public void setTrainName(String trainName) {
//        this.trainName = trainName;
//    }

//    public String getJourneyDate() {
//        return journeyDate;
//    }
//
//    public void setJourneyDate(String journeyDate) {
//        this.journeyDate = journeyDate;
//    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public LocalDate getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
	}
	
	
    
    
}
package com.ortb.booking.dto;

import java.time.LocalDate;
import java.util.List;

//import com.ortb.booking.model.Passenger;
//import com.ortb.booking.model.Payment;

public class BookingDTO {
	//private String pnr;
	private Long trainId;
    private LocalDate journeyDate;
    private String sourceStation;
    private String destinationStation;
    private String seatClass;
    private String quota;
    private Double totalFare;
    private String bookingStatus;
    private List<PassengerDTO> passengers;
    private PaymentDTO payment;
    
//	public String getPnr() {
//		return pnr;
//	}
//	public void setPnr(String pnr) {
//		this.pnr = pnr;
//	}
	public LocalDate getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
	}
	
	
	public String getSourceStation() {
		return sourceStation;
	}
	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}
	public String getDestinationStation() {
		return destinationStation;
	}
	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
	public Double getTotalFare() {
		return totalFare;
	}
	public String getSeatClass() {
		return seatClass;
	}
	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
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
//	public List<Passenger> getPassengers() {
//		return passengers;
//	}
//	public void setPassengers(List<Passenger> passengers) {
//		this.passengers = passengers;
//	}
//	public Payment getPayment() {
//		return payment;
//	}
//	public void setPayment(Payment payment) {
//		this.payment = payment;
//	}
	public Long getTrainId() {
		return trainId;
	}
	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}
	
	public List<PassengerDTO> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerDTO> passengers) {
		this.passengers = passengers;
	}
	public PaymentDTO getPayment() {
		return payment;
	}
	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}
	//	@Override
//	public String toString() {
//		return "BookingDTO [pnr=" + pnr + ", trainId=" + trainId + ", journeyDate=" + journeyDate + ", totalFare="
//				+ totalFare + ", bookingStatus=" + bookingStatus + ", passengers=" + passengers + ", payment=" + payment
//				+ ", getPnr()=" + getPnr() + ", getJourneyDate()=" + getJourneyDate() + ", getTotalFare()="
//				+ getTotalFare() + ", getBookingStatus()=" + getBookingStatus() + ", getPassengers()=" + getPassengers()
//				+ ", getPayment()=" + getPayment() + ", getTrainId()=" + getTrainId() + ", getClass()=" + getClass()
//				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
//	}
	@Override
	public String toString() {
		return "BookingDTO [trainId=" + trainId + ", journeyDate=" + journeyDate + ", sourceStation=" + sourceStation
				+ ", destinationStation=" + destinationStation + ", seatClass=" + seatClass + ", quota=" + quota
				+ ", totalFare=" + totalFare + ", bookingStatus=" + bookingStatus + ", passengers=" + passengers
				+ ", payment=" + payment + "]";
	}
	
	
	
	
	
	
    // Getters and setters
    
}
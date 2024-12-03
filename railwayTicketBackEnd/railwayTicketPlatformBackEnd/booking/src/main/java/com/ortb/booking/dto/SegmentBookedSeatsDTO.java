package com.ortb.booking.dto;

public class SegmentBookedSeatsDTO {
    private Long segmentId;
    private int totalSeats;
    private int bookedSeats;
    
    
    public SegmentBookedSeatsDTO(Long segmentId, int totalSeats, int bookedSeats) {
        this.segmentId = segmentId;
        this.totalSeats = totalSeats;
        this.bookedSeats = bookedSeats;
    }
    
	public Long getSegmentId() {
		return segmentId;
	}
	public void setSegmentId(Long segmentId) {
		this.segmentId = segmentId;
	}
	
	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
    
    

}

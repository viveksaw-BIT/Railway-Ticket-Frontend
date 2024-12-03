package com.ortb.booking.dto;

public class SeatDTO {
	
	private String seatClass;
	private String quota;
	private Long count;
	
	
	
	public SeatDTO(String seatClass, String quota, Long count) {
		super();
		this.seatClass = seatClass;
		this.quota = quota;
		this.count = count;
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
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "SeatDTO [seatClass=" + seatClass + ", quota=" + quota + ", count=" + count + "]";
	}
	
	
	
	    
}

package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Reservations {
	
	private Long reservationId;
	private Long siteId;
	private String name;
	private Date fromDate;
	private Date toDate;
	private LocalDate dateEntered;
	private BigDecimal dailyFee;
	
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public LocalDate getDateEntered() {
		return dateEntered;
	}
	public void setDateEntered(LocalDate dateEntered) {
		this.dateEntered = dateEntered;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	
	
	

}

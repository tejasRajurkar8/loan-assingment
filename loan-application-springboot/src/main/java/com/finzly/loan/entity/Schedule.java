package com.finzly.loan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer scheduleId;
	
	Integer loanId;
	Date paymentDate;
	Double principal;
	Double projectedInterest;
	Double paymentAmount;
	String status;
	
	
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Double getPrincipal() {
		return principal;
	}
	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	public Double getProjectedInterest() {
		return projectedInterest;
	}
	public void setProjectedInterest(Double projectedInterest) {
		this.projectedInterest = projectedInterest;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Schedule(Integer scheduleId, Integer loanId, Date paymentDate, Double principal, Double projectedInterest,
			Double paymentAmount, String status) {
		super();
		this.scheduleId = scheduleId;
		this.loanId = loanId;
		this.paymentDate = paymentDate;
		this.principal = principal;
		this.projectedInterest = projectedInterest;
		this.paymentAmount = paymentAmount;
		this.status = status;
	}
	public Schedule() {
		super();
	}
	
	
}

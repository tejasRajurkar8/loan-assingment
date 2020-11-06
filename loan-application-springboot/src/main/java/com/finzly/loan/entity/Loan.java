package com.finzly.loan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Loan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer loanId;
	
	Integer custId;
	Integer paymentFrequency;
	Integer tenure;
	Double loanAmount;
	Double interestRate;
	Date TradeDate;
	Date loanStartDate;
	Date maturityDate;
	Boolean evenPrincipal;
	
	public Integer getLoanId() {
		return loanId;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public Integer getCustId() {
		return custId;
	}
	
	public Integer getTenure() {
		return tenure;
	}
	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public Integer getPaymentFrequency() {
		return paymentFrequency;
	}
	public void setPaymentFrequency(Integer paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public Date getTradeDate() {
		return TradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		TradeDate = tradeDate;
	}
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public Boolean getEvenPrincipal() {
		return evenPrincipal;
	}
	public void setEvenPrincipal(Boolean evenPrincipal) {
		this.evenPrincipal = evenPrincipal;
	}
	

	

}

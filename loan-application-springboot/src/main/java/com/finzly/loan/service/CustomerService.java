package com.finzly.loan.service;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finzly.loan.entity.Loan;
import com.finzly.loan.entity.Schedule;
import com.finzly.loan.repository.CustomerRepository;
import com.finzly.loan.repository.ScheduleRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	Loan newLoan;
	
	final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	public Iterable<Loan> getAllLoans(){
		logger.info("Viewing all the loans...");
		return customerRepository.findAll();
	}
	
	public List<Loan> getLoans(Integer custId){
		logger.info("Viewing all the loans for customer id "+ custId);
		return customerRepository.findByCustId(custId);
	}
	
	public Loan addLoan(Loan loan) {
		this.newLoan = customerRepository.save(loan);
		logger.info("Created a loan for customer id " + loan.getCustId());
		
		int emiTenure = loan.getTenure()/loan.getPaymentFrequency();                  // calculating no of EMIs
		
		Double loanAmount = loan.getLoanAmount();
		Double principal = (loanAmount/emiTenure);
		for(int i=0; i< emiTenure ; i++) {
			Schedule schedule = new Schedule();
			schedule.setLoanId(loan.getLoanId());
			
			Calendar loanDate = Calendar.getInstance();
			loanDate.setTime(loan.getLoanStartDate());
			loanDate.add(Calendar.MONTH,(i+1)*loan.getPaymentFrequency());           // calculating EMI month
			schedule.setPaymentDate(loanDate.getTime());
			
			
			if(loan.getEvenPrincipal()) {                                           // for even principal
				
				schedule.setPrincipal(Double.parseDouble(String.format("%.2f", principal)));
				
				
			}
			else {                                                                 // for interest only
				if(i == (emiTenure-1)) {                                           // for last EMI set principal = loanAmount
					schedule.setPrincipal(loan.getLoanAmount());
				}
				else {
					schedule.setPrincipal(0.0);
				}
			}
			
			Double interest = (loanAmount*loan.getInterestRate())/(100* emiTenure); 
			loanAmount = loanAmount - principal;                                  // calculating total EMI amount
			schedule.setProjectedInterest(Double.parseDouble(String.format("%.2f", interest)));
			schedule.setPaymentAmount(Double.parseDouble(String.format("%.2f", schedule.getPrincipal() + schedule.getProjectedInterest())));
			
			
			Calendar currentDate = Calendar.getInstance();                        // set status = PAID if currentDate>EmiDate | PROJECTED if currentDate<emiDate | else AWAITINGPAYMENT 
			                                                                
			if(currentDate.get(Calendar.YEAR) > loanDate.get(Calendar.YEAR)) {
				schedule.setStatus("PAID");
			}
			else if(currentDate.get(Calendar.YEAR) < loanDate.get(Calendar.YEAR)) {
				schedule.setStatus("PROJECTED");
			}
			else if(currentDate.get(Calendar.YEAR) == loanDate.get(Calendar.YEAR)) {
					if(currentDate.get(Calendar.MONTH) > loanDate.get(Calendar.MONTH)) {
						schedule.setStatus("PAID");
					}
					else if(currentDate.get(Calendar.MONTH) < loanDate.get(Calendar.MONTH)) {
						schedule.setStatus("PROJECTED");
					}
					else if(currentDate.get(Calendar.MONTH) == loanDate.get(Calendar.MONTH)) {
						if(currentDate.get(Calendar.DATE) > loanDate.get(Calendar.DATE)) {
							schedule.setStatus("PAID");
						}
						else if(currentDate.get(Calendar.DATE) < loanDate.get(Calendar.DATE)) {
							schedule.setStatus("PROJECTED");
						}
						else if(currentDate.get(Calendar.DATE) == loanDate.get(Calendar.DATE)){
							schedule.setStatus("AWAITINGPAYMENT");
						}
					}
				
			}
			if(i == (emiTenure-1)) {
				loan.setMaturityDate(loanDate.getTime());
				this.newLoan = customerRepository.save(loan);
			}
			
			scheduleRepository.save(schedule);
			
			
		}
		logger.info("Created EMI schedule for customer id "+ loan.getCustId());
		loan = new Loan();
		return this.newLoan;
	}
}

package com.finzly.loan.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finzly.loan.entity.Loan;
import com.finzly.loan.entity.Schedule;
import com.finzly.loan.repository.LoanRepository;
import com.finzly.loan.service.ScheduleService;
@Service
public class LoanService {
	
	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private ScheduleService scheduleService;
	
	Loan newLoan;
	
	final static Logger logger = LoggerFactory.getLogger(LoanService.class);
	
	public Iterable<Loan> getAllLoans(){
		logger.info("Viewing all the loans...");
		return loanRepository.findAll();
	}
	
	public List<Loan> getLoans(Integer custId){
		logger.info("Viewing all the loans for customer id "+ custId);
		return loanRepository.findByCustId(custId);
	}
	
	public Loan addLoan(Loan loan) {
		newLoan = loanRepository.save(loan);
		logger.info("Created a loan for customer id " + loan.getCustId());
		
		List<Schedule> schedules = scheduleService.addSchedule(loan);
		logger.info("Created EMI schedule for customer id "+ loan.getCustId());
		loan = new Loan();
		
		return newLoan;
	}
}

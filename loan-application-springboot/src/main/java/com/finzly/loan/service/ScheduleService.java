package com.finzly.loan.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finzly.loan.entity.Loan;
import com.finzly.loan.entity.Schedule;
import com.finzly.loan.repository.ScheduleRepository;
import com.finzly.loan.repository.LoanRepository;

@Service
public class ScheduleService {
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	LoanRepository loanRepository;
	
	final static Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	
	public List<Schedule> getSchedules( Integer loanId){
		logger.info("Viewing Schedule for loan id "+ loanId);
		return scheduleRepository.findAllByLoanId(loanId);
	}
	
	public List<Schedule> addSchedule(Loan loan) {
	
		
		int emiTenure = loan.getTenure()/loan.getPaymentFrequency();      // calculating no of EMIs      
		
		               
        Loan newLoan = new Loan();
        List<Schedule> schedules = new ArrayList<Schedule>();
		Double loanAmount = loan.getLoanAmount();
		Double principal = (loanAmount/emiTenure);
		Double interest =0.0 ;
		for(int emiNumber=0; emiNumber< emiTenure ; emiNumber++) {
			Schedule schedule = new Schedule();
			schedule.setLoanId(loan.getLoanId());
			Calendar loanDate = Calendar.getInstance();
			loanDate.setTime(loan.getLoanStartDate());
			loanDate.add(Calendar.MONTH,(emiNumber+1)*loan.getPaymentFrequency());           // calculating EMI month
			schedule.setPaymentDate(loanDate.getTime());
			
			
			if(loan.getEvenPrincipal()) {                                           // for even principal
				
				schedule.setPrincipal(Double.parseDouble(String.format("%.2f", principal)));
				interest = (loanAmount*loan.getInterestRate())/(100* emiTenure); 
				loanAmount = loanAmount - principal;                                  // calculating total EMI amount
				
			}
			else {                                                                 // for interest only
				if(emiNumber == (emiTenure-1)) {                                           // for last EMI set principal = loanAmount
					schedule.setPrincipal(loan.getLoanAmount());

				}
				else {
					schedule.setPrincipal(0.0);
				}
				interest = (loanAmount*loan.getInterestRate())/(100* emiTenure);                                  // calculating total EMI amount
			}
			
//			Double interest = (loanAmount*loan.getInterestRate())/(100* emiTenure); 
//			loanAmount = loanAmount - principal;                                  // calculating total EMI amount
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
			if(emiNumber == (emiTenure-1)) {
				loan.setMaturityDate(schedule.getPaymentDate());
				newLoan = loanRepository.save(loan);
			}
			scheduleRepository.save(schedule);
			schedules.add(schedule);
			
		}
		
		return schedules;
		
		
	}
	

}

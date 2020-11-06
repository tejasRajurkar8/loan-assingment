package com.finzly.loan.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finzly.loan.entity.Schedule;
import com.finzly.loan.repository.ScheduleRepository;

@Service
public class ScheduleService {
	@Autowired
	ScheduleRepository scheduleRepository;
	
	final static Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	
	public List<Schedule> getSchedules( Integer loanId){
		logger.info("Viewing Schedule for loan id "+ loanId);
		return scheduleRepository.findAllByLoanId(loanId);
	}

}

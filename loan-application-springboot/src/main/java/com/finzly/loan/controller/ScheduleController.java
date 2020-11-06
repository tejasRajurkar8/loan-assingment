package com.finzly.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.finzly.loan.entity.Schedule;
import com.finzly.loan.service.ScheduleService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ScheduleController {
	
	@Autowired
	ScheduleService scheduleService;
	
	@GetMapping(path= "/schedules/{loanId}")
	public List<Schedule> getSchedules(@PathVariable Integer loanId){
		return scheduleService.getSchedules(loanId);
	}
}

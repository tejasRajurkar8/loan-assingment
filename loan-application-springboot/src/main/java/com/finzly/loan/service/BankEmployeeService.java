package com.finzly.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finzly.loan.entity.BankEmployee;
import com.finzly.loan.repository.BankEmployeeRepository;
import org.slf4j.*;


@Service
public class BankEmployeeService {
	
	@Autowired
	BankEmployeeRepository bankEmployeeRepository;
	
	final static Logger logger = LoggerFactory.getLogger(BankEmployeeService.class);
	
	public BankEmployee authenticate(String employeeName , String password) {
		BankEmployee employee = bankEmployeeRepository.findByEmployeeName(employeeName);
		if(employee != null && employee.getPassword().equals(password)) {
			logger.info("Employee Authenticated!");
			return employee;
		}
		
		
		return null;
	}
}

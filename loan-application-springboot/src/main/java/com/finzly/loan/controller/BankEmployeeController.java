package com.finzly.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.finzly.loan.entity.BankEmployee;
import com.finzly.loan.service.BankEmployeeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BankEmployeeController {
	
	@Autowired
	BankEmployeeService bankEmployeeService;
	
	@GetMapping(path="/authenticate/{employeeName}/{password}")
	public BankEmployee authenticate(@PathVariable String employeeName, @PathVariable String password  ) {
		return bankEmployeeService.authenticate(employeeName, password);
		
	}
}

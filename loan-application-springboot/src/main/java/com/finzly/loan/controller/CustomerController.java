package com.finzly.loan.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finzly.loan.entity.Loan;

import com.finzly.loan.service.CustomerService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerController {
	
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(path = "/loans")
	public Iterable<Loan> getAllLoans(){
		return customerService.getAllLoans();
	}
	
	
	@GetMapping(path = "/loans/{custId}")
	public List<Loan> getLoans(@PathVariable String custId){
		Integer id = Integer.parseInt(custId);
		return customerService.getLoans(id);
	}
	
	@PostMapping(path = "/loans/add")
	public Loan addLoan(@RequestBody Loan loan){
		return customerService.addLoan(loan);
	}
	
	
}

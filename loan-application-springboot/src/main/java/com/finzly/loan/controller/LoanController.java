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
import com.finzly.loan.service.LoanService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoanController {
	
	
	@Autowired
	private LoanService loanService;
	
	@GetMapping(path = "/loans")
	public Iterable<Loan> getAllLoans(){
		return loanService.getAllLoans();
	}
	
	
	@GetMapping(path = "/loans/{custId}")
	public List<Loan> getLoans(@PathVariable String custId){
		Integer id = Integer.parseInt(custId);
		return loanService.getLoans(id);
	}
	
	@PostMapping(path = "/loans/post")
	public Loan addLoan(@RequestBody Loan loan){
		return loanService.addLoan(loan);
	}
	
	
}

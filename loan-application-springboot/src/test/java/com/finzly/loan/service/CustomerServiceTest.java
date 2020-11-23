package com.finzly.loan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.finzly.loan.entity.Loan;
import com.finzly.loan.entity.Schedule;
import com.finzly.loan.repository.LoanRepository;
import com.finzly.loan.repository.ScheduleRepository;
import com.finzly.loan.service.*;



class CustomerServiceTest {
	
	@InjectMocks
	private LoanService loanService;
	
	@Mock
	private LoanRepository loanRepository;
	
	@Mock
	private ScheduleService scheduleService;
	
	@Mock
	private ScheduleRepository scheduleRepository;
	
	Loan loan, loan1;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
//	@BeforeClass
//	void setLoans() throws Exception{
//		loan = new Loan();
//		loan.setCustId(1);
//		loan.setEvenPrincipal(true);
//		loan.setInterestRate(10.5);
//		loan.setLoanAmount(10000.00);
//		loan.setLoanId(1);
//		loan.setLoanStartDate(new Date());
//		loan.setTradeDate(new Date());
//		loan.setMaturityDate(new Date());
//		loan.setPaymentFrequency(4);
//		loan.setTenure(12);
//		
//		
//		loan1 = new Loan();
//		loan1.setCustId(2);
//		loan1.setEvenPrincipal(false);
//		loan1.setInterestRate(10.5);
//		loan1.setLoanAmount(10000.00);
//		loan1.setLoanId(1);
//		loan1.setLoanStartDate(new Date());
//		loan1.setTradeDate(new Date());
//		loan1.setMaturityDate(new Date());
//		loan1.setPaymentFrequency(12);
//		loan1.setTenure(12);
//	}

	@Test
	void GetAllLoansShouldReturnListofLoans() {
		List<Loan> loans = new ArrayList<Loan>();
		loans.add(loan);
		loans.add(loan1);
		Mockito.when(loanRepository.findAll()).thenReturn(loans);
		assertIterableEquals(loans, loanService.getAllLoans());
	}
	
	@Test
	void GetAllLoansShouldReturnEmptyList() {
		List<Loan> loans = new ArrayList<Loan>();
		
		Mockito.when(loanRepository.findAll()).thenReturn(loans);
		assertIterableEquals(loans, loanService.getAllLoans());
	}

	@Test
	void GetAllLoansOfCustomer() {
		//fail("Not yet implemented");
		
		
		List<Loan> loans = new ArrayList<Loan>();
		loans.add(loan);
		
		
		Mockito.when(loanRepository.findByCustId(1)).thenReturn(loans);
		
		assertNotNull(loanService.getLoans(1));
	}
	
	
	@Test
	void GetLoansofCustomerShouldReturnEmptyList() {
		
		List<Loan> loans = new ArrayList<Loan>();
		loans.add(loan);
		
		List<Loan> expected = new ArrayList<Loan>();
		
		Mockito.when(loanRepository.findByCustId(1)).thenReturn(loans);
		
		assertIterableEquals(expected ,loanService.getLoans(0));
	}

	@Test
	void PostValidLoanOfCustomer() {
		
		Loan newLoan = new Loan();
		newLoan.setCustId(1);
		newLoan.setEvenPrincipal(true);
		newLoan.setInterestRate(10.5);
		newLoan.setLoanAmount(10000.00);
		newLoan.setLoanId(1);
		newLoan.setLoanStartDate(new Date());
		newLoan.setTradeDate(new Date());
		//newLoan.setMaturityDate(new Date());
		newLoan.setPaymentFrequency(4);
		newLoan.setTenure(12);
		
		
		Schedule schedule = new Schedule();
		
		Mockito.when(loanRepository.save(newLoan)).thenReturn(newLoan);
		assertEquals(newLoan, loanService.addLoan(newLoan));
		
	}
	
	@Test
	void testAddNullLoan() {
		
		Loan newLoan = new Loan();
		
		
		
		Schedule schedule = new Schedule();
		
		Mockito.when(loanRepository.save(newLoan)).thenReturn(newLoan);
		assertEquals(newLoan, loanService.addLoan(newLoan));
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void checkMaturityDateIsValid() {
		 Loan newLoan = new Loan(3,1,12,12,100000.00,10.00, new Date(), new Date(), null,true);
		 Schedule schedule = new Schedule();
		 Mockito.when(scheduleRepository.save(schedule)).thenReturn(schedule);
		 Mockito.when(loanRepository.save(newLoan)).thenReturn(newLoan);
		 
	}

}

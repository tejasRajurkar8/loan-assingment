package com.finzly.loan.service;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.finzly.loan.service.ScheduleService;


class ScheduleServiceTest {
	
	@InjectMocks
	private ScheduleService scheduleService;
	
	@Mock
	private ScheduleRepository scheduleRepository;
	
	@Mock
	private LoanRepository loanRepository;
	
	List<Schedule> schedules;
	Schedule schedule1;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

//	@Before
//	void setSchedules() throws Exception {
//		
//		schedule1 = new Schedule();
//		schedule1.setLoanId(1);
//		schedule1.setPaymentAmount(10000.00);
//		schedule1.setPaymentDate(new Date());
//		schedule1.setPrincipal(5000.00);
//		schedule1.setProjectedInterest(5000.00);
//		schedule1.setStatus("AWAITINGPAYMENT");
//		
//		
//		
//	}

	@Test
	void testGetSchedules() {

		schedule1 = new Schedule();
		schedule1.setLoanId(1);
		schedule1.setPaymentAmount(10000.00);
		schedule1.setPaymentDate(new Date());
		schedule1.setPrincipal(5000.00);
		schedule1.setProjectedInterest(5000.00);
		schedule1.setStatus("AWAITINGPAYMENT");
		
		schedules = new ArrayList<Schedule>();
		schedules.add(schedule1);
		Mockito.when(scheduleRepository.findAllByLoanId(1)).thenReturn(schedules);
		assertIterableEquals(schedules, scheduleService.getSchedules(1));
	}
	
	@Test
	void testGetSchedulesShouldReturnEmptyList() {
		
		Mockito.when(scheduleRepository.findAllByLoanId(1)).thenReturn(schedules);
		assertIterableEquals(schedules, scheduleService.getSchedules(1));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void checkMaturityDateOfLoan() throws ParseException {
		
		Loan loan = new Loan(3,1,12,12,100000.00,10.00, new Date(), new Date(), null,true);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		scheduleService.addSchedule(loan);
		 String sDate1="23/11/2021";  
		    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
		assertEquals(date1.getDate(), loan.getMaturityDate().getDate());
		assertEquals(date1.getMonth(), loan.getMaturityDate().getMonth());
		assertEquals(date1.getYear(), loan.getMaturityDate().getYear());
		
		
	}
	
	@Test
	void paymentFrequencyIsZeroThrowDivideByZeroException() {
		
		Loan loan = new Loan(3,1,0,12,100000.00,10.00, new Date(), new Date(), null,true);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		
		assertThrows(ArithmeticException.class, () -> {
			scheduleService.addSchedule(loan);
		  });
		
		
	}
	
	
	@Test
	void paymentFrequencyMonthlyAndTenure12EvenPrincipal() {
		Loan loan = new Loan(3,1,1,12,10000.00,12.00, new Date(), new Date(), null,true);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		assertEquals(833.33, actualSchedule.get(0).getPrincipal());
		assertEquals(100.00, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
	}
	
	@Test
	void paymentFrequencyQuaterlyAndTenure12EvenPrincipal() {
		Loan loan = new Loan(3,1,3,12,10000.00,12.00, new Date(), new Date(), null,true);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		assertEquals(2500, actualSchedule.get(0).getPrincipal());
		assertEquals(300, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
	}
	
	@Test
	void paymentFrequencyHalfYearlyAndTenure12EvenPrincipal() {
		Loan loan = new Loan(3,1,6,12,10000.00,12.00, new Date(), new Date(), null,true);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		
		assertEquals(2, actualSchedule.size());
		assertEquals(5000, actualSchedule.get(0).getPrincipal());
		assertEquals(600, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
	}
	
	@Test
	void paymentFrequencyYearlyAndTenure12EvenPrincipal() {
		Loan loan = new Loan(3,1,12,12,10000.00,12.00, new Date(), new Date(), null,true);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		assertEquals(10000, actualSchedule.get(0).getPrincipal());
		assertEquals(1200, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
	}
	
	@Test
	void paymentFrequencyYearlyAndTenure12InterestOnly() {
		Loan loan = new Loan(3,1,12,12,10000.00,12.00, new Date(), new Date(), null,false);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		assertEquals(10000, actualSchedule.get(0).getPrincipal());
		assertEquals(1200, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
	}
	
	@Test
	void paymentFrequencyHalfYearlyAndTenure12InterestOnly() {
		Loan loan = new Loan(3,1,6,12,10000.00,12.00, new Date(), new Date(), null,false);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		
		assertEquals(2, actualSchedule.size());
		
		assertEquals(0, actualSchedule.get(0).getPrincipal());
		assertEquals(600, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
		assertEquals(10000, actualSchedule.get(1).getPrincipal());
		assertEquals(600, actualSchedule.get(1).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(1).getStatus());
		
	}
	
	@Test
	void paymentFrequencyQuaterlyAndTenure12InterestOnly() {
		Loan loan = new Loan(3,1,3,12,10000.00,12.00, new Date(), new Date(), null,false);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		
		assertEquals(4, actualSchedule.size());
		
		assertEquals(0, actualSchedule.get(0).getPrincipal());
		assertEquals(300, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
		assertEquals(10000, actualSchedule.get(3).getPrincipal());
		assertEquals(300, actualSchedule.get(3).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(3).getStatus());
		
	}
	
	@Test
	void paymentFrequencyMonthlyAndTenure12InterestOnly() {
		Loan loan = new Loan(3,1,1,12,10000.00,12.00, new Date(), new Date(), null,false);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		
		assertEquals(12, actualSchedule.size());
		
		assertEquals(0, actualSchedule.get(0).getPrincipal());
		assertEquals(100, actualSchedule.get(0).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(0).getStatus());
		
		assertEquals(10000, actualSchedule.get(11).getPrincipal());
		assertEquals(100, actualSchedule.get(11).getProjectedInterest());
		assertEquals("PROJECTED", actualSchedule.get(11).getStatus());
		
	}
	
	@Test
	void shouldReturnAwaitingPaymentStatus() throws ParseException {
		 String sDate1="23/10/2020";  
		    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1); 
		Loan loan = new Loan(3,1,1,12,10000.00,12.00, date1, date1, null,false);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		
		assertEquals(12, actualSchedule.size());
		
		assertEquals("AWAITINGPAYMENT", actualSchedule.get(0).getStatus());
	
		
		
	}

	
	@Test
	void shouldReturnProjectedStatus() throws ParseException {
		 String sDate1="23/10/2020";  
		    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1); 
		Loan loan = new Loan(3,1,1,12,10000.00,12.00, date1, date1, null,false);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		
		assertEquals(12, actualSchedule.size());
		
		assertEquals("PROJECTED", actualSchedule.get(1).getStatus());
	
		
		
	}
	
	@Test
	void shouldReturnPaidStatus() throws ParseException {
		 String sDate1="23/09/2020";  
		    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1); 
		Loan loan = new Loan(3,1,1,12,10000.00,12.00, date1, date1, null,false);
		Mockito.when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
		List<Schedule> actualSchedule = scheduleService.addSchedule(loan);
		
		assertEquals(12, actualSchedule.size());
		
		assertEquals("PAID", actualSchedule.get(0).getStatus());
	
		
		
	}



}

package com.finzly.loan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finzly.loan.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

	List<Schedule> findAllByLoanId(Integer loanId);

}

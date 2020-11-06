package com.finzly.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finzly.loan.entity.BankEmployee;

public interface BankEmployeeRepository extends JpaRepository<BankEmployee, Integer> {
	BankEmployee findByEmployeeName(String employeeName);
}

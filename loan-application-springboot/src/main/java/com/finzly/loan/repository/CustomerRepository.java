package com.finzly.loan.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finzly.loan.entity.Loan;




public interface CustomerRepository extends JpaRepository<Loan, Integer> {

	List<Loan> findByCustId(Integer custId);
}

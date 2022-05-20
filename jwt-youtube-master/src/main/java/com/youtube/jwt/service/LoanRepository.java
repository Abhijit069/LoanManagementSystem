package com.youtube.jwt.service;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.youtube.jwt.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan,Integer> {
	
	@Query("from Loan where loanNo=?1")
	Loan getById(String no);
	
	@Query("from Loan where fName=?1")
	ArrayList<Loan> getByFname(String fName);
	
	@Query("from Loan where lName=?1")
	ArrayList<Loan> getByLname(String llName);
	
	@Query("from Loan where lName=?1 and fName=?1")
	ArrayList<Loan> getByFullname(String llName, String fName);
}



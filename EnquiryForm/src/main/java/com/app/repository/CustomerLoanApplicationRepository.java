package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.CustomerLoanApplication;

@Repository
public interface CustomerLoanApplicationRepository extends JpaRepository<CustomerLoanApplication, Integer>{

	CustomerLoanApplication findByCustomerLoanID(int customerLoanID);
	
	List<CustomerLoanApplication> findAllByLoanStatus(String loanStatus);
	

}

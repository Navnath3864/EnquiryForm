package com.app.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.EnquiryDetails;
@Repository
public interface EnquiryDetailsRepository extends JpaRepository<EnquiryDetails, Integer> {
		EnquiryDetails findByCustomerID(int customerID);
	
}

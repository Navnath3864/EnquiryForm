package com.app.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.model.CustomerLoanApplication;
import com.app.model.EnquiryDetails;

import jakarta.validation.Valid;


public interface EnquiryDetailsService {

	

	List<EnquiryDetails> getAllEquiryDetails();

	EnquiryDetails getSingleEnquiryDetails(int customerID);
	void deleteEnquiryDetails(int customerID);

	EnquiryDetails updateEnquiryDetails(EnquiryDetails enquiryDetails, int customerID);


	CustomerLoanApplication saveCustomerLoanApplicationForm(CustomerLoanApplication customerLoanApplication);

	EnquiryDetails updateEnquiry(EnquiryDetails enquiryDetails);

	EnquiryDetails checkLoginUser(String email, String password);

	boolean updateCibilStatus(int customerID,String enquiryStatus);

	List<EnquiryDetails> enquiryDetailsService();

	EnquiryDetails saveDetails(@Valid String enquiryDetails, MultipartFile profilePhoto);

	

	
}

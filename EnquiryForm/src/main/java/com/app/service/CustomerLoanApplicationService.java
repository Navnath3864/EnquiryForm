package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.model.AllPersonalDocs;
import com.app.model.CustomerLoanApplication;
import com.app.model.LoanDisbursement;

public interface CustomerLoanApplicationService {
	public CustomerLoanApplication saveDetails(String customerLoanApplication, int id, MultipartFile addressProof,
			MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard, MultipartFile photo,
			MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips);

	public List<CustomerLoanApplication> getAllCustomerApplicationData();

	public CustomerLoanApplication updateLoanStatus(int id, String loanStatus);

	public List<CustomerLoanApplication> getAllLoansubmited();

	public List<CustomerLoanApplication> getAllVerifiedData();

	public CustomerLoanApplication updateLoanStatusofCustomerApplication(int id, String loanStatus);

	public List<CustomerLoanApplication> getAllSanctioedData();

	public CustomerLoanApplication updateLoandisBursement(int customerLoanId,
			LoanDisbursement loanDisbursement);


	public AllPersonalDocs updateDocument(int customerid, MultipartFile addressProof, MultipartFile panCard,
			MultipartFile incomeTax, MultipartFile addharCard, MultipartFile photo, MultipartFile signature,
			MultipartFile bankCheque, MultipartFile salarySlips);


	 

	public CustomerLoanApplication getCustomerLoanApplication(int customerLoanID);

	public AllPersonalDocs getAllPersonalDocs(int documentID);

	public List<CustomerLoanApplication> getAllUserAcceptedSactionedData();

	public CustomerLoanApplication getSingleCustomerLoanApplication(int customerLoanID);

	


}

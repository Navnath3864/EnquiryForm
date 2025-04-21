
package com.app.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.AllPersonalDocs;
import com.app.model.CustomerLoanApplication;
import com.app.model.EnquiryDetails;
import com.app.model.LoanDisbursement;
import com.app.repository.AllPersonalDocumentsRepository;
import com.app.repository.CustomerLoanApplicationRepository;
import com.app.repository.EnquiryDetailsRepository;
import com.app.service.CustomerLoanApplicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerLoanApplicationServiceImpl implements CustomerLoanApplicationService {

	@Autowired
	CustomerLoanApplicationRepository customerLoanApplicationRepository;

	@Autowired
	EnquiryDetailsRepository enquiryDetailsRepository;

	@Autowired
	AllPersonalDocumentsRepository allPersonalDocumentsRepository;
	
	@Override
	public CustomerLoanApplication saveDetails(String customerLoanApplication1, int id,
	        MultipartFile addressProof, MultipartFile panCard, MultipartFile incomeTax,
	        MultipartFile addharCard, MultipartFile photo, MultipartFile signature,
	        MultipartFile bankCheque, MultipartFile salarySlips) {

	    EnquiryDetails details = enquiryDetailsRepository.findByCustomerID(id);

	    ObjectMapper objectMapper = new ObjectMapper();
	    CustomerLoanApplication customerLoanApplication;

	    try {
	        customerLoanApplication = objectMapper.readValue(customerLoanApplication1, CustomerLoanApplication.class);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to parse CustomerLoanApplication JSON", e);
	    }

	    // Ensure personal docs are initialized
	    AllPersonalDocs docs = customerLoanApplication.getAllPersonalDocument();
	    if (docs == null) {
	        docs = new AllPersonalDocs();
	        customerLoanApplication.setAllPersonalDocument(docs);
	    }

	    try {
	        docs.setAddressProof(addressProof.getBytes());
	        docs.setAddharCard(addharCard.getBytes());
	        docs.setPanCard(panCard.getBytes());
	        docs.setIncomeTax(incomeTax.getBytes());
	        docs.setPhoto(photo.getBytes());
	        docs.setSignature(signature.getBytes());
	        docs.setBankCheque(bankCheque.getBytes());
	        docs.setSalarySlips(salarySlips.getBytes());
	    } catch (IOException e) {
	        throw new RuntimeException("Error reading uploaded files", e);
	    }

	    // Set relationship
	    details.setCustomerLoanApplication(customerLoanApplication);
	    
	    // Save only one side
	    enquiryDetailsRepository.save(details);

	    return customerLoanApplication;
	}


	/*
	 * @Override public CustomerLoanApplication saveDetails(String
	 * customerLoanApplication1, int id, MultipartFile addressProof, MultipartFile
	 * panCard, MultipartFile incomeTax, MultipartFile addharCard, MultipartFile
	 * photo,
	 * 
	 * MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips)
	 * {
	 * 
	 * CustomerLoanApplication customerLoanApplication = null;
	 * 
	 * EnquiryDetails details = enquiryDetailsRepository.findByCustomerID(id);
	 * ObjectMapper objectMapper = new ObjectMapper();
	 * 
	 * try {
	 * 
	 * customerLoanApplication = objectMapper.readValue(customerLoanApplication1,
	 * CustomerLoanApplication.class); } catch (JsonMappingException e) {
	 * e.printStackTrace();
	 * 
	 * } catch (JsonProcessingException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * }
	 *//***/
	/*
	 * if (customerLoanApplication.getAllPersonalDocument() == null) {
	 * customerLoanApplication.setAllPersonalDocument(new AllPersonalDocs()); }
	 * 
	 * try {
	 * customerLoanApplication.getAllPersonalDocument().setAddressProof(addressProof
	 * .getBytes());
	 * customerLoanApplication.getAllPersonalDocument().setAddharCard(addharCard.
	 * getBytes());
	 * customerLoanApplication.getAllPersonalDocument().setPanCard(panCard.getBytes(
	 * )); customerLoanApplication.getAllPersonalDocument().setIncomeTax(incomeTax.
	 * getBytes());
	 * customerLoanApplication.getAllPersonalDocument().setPhoto(photo.getBytes());
	 * customerLoanApplication.getAllPersonalDocument().setSignature(signature.
	 * getBytes());
	 * customerLoanApplication.getAllPersonalDocument().setBankCheque(bankCheque.
	 * getBytes());
	 * customerLoanApplication.getAllPersonalDocument().setSalarySlips(salarySlips.
	 * getBytes());
	 * 
	 * System.out.println(customerLoanApplication);
	 * details.setCustomerLoanApplication(customerLoanApplication);
	 * enquiryDetailsRepository.save(details);
	 * 
	 * // customerLoanApplicationRepository.save(customerLoanApplication);
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * return customerLoanApplication;
	 * 
	 * }
	 * 
	 */
	
	
	@Override
	public List<CustomerLoanApplication> getAllCustomerApplicationData() {

		return customerLoanApplicationRepository.findAll();
	}

	@Override
	public CustomerLoanApplication updateLoanStatus(int id, String loanStatus) {
		CustomerLoanApplication customerLoanApplication = customerLoanApplicationRepository.findByCustomerLoanID(id);
		customerLoanApplication.setLoanStatus(loanStatus);
		CustomerLoanApplication customerLoanApplication2 = customerLoanApplicationRepository
				.save(customerLoanApplication);
		return customerLoanApplication2;
	}

	@Override
	public List<CustomerLoanApplication> getAllLoansubmited() {
		String status = "Submit";
		return customerLoanApplicationRepository.findAllByLoanStatus(status);
	}

	@Override
	public List<CustomerLoanApplication> getAllVerifiedData() {
		String status = "Document Verified";
		return customerLoanApplicationRepository.findAllByLoanStatus(status);
	}

	@Override
	public CustomerLoanApplication updateLoanStatusofCustomerApplication(int id, String loanStatus) {
		CustomerLoanApplication customerLoanApplication = customerLoanApplicationRepository.findByCustomerLoanID(id);
		customerLoanApplication.setLoanStatus(loanStatus);
		CustomerLoanApplication customerLoanApplication2 = customerLoanApplicationRepository
				.save(customerLoanApplication);
		return customerLoanApplication2;

	}

	@Override
	public List<CustomerLoanApplication> getAllSanctioedData() {
		String status = "Loan Sanctioned";
		return customerLoanApplicationRepository.findAllByLoanStatus(status);
	}

	@Override
	public CustomerLoanApplication updateLoandisBursement(int customerLoanId, LoanDisbursement loanDisbursement) {

		Optional<CustomerLoanApplication> customerLoanapp = customerLoanApplicationRepository.findById(customerLoanId);
		if (customerLoanapp.isPresent()) {
			loanDisbursement.setAccountNumber(customerLoanapp.get().getAccountDetails().getAccountNumber());
			loanDisbursement.setAccountType(customerLoanapp.get().getAccountDetails().getAccounType());
			customerLoanapp.get().setLoandisbursement(loanDisbursement);
			System.out.println(customerLoanapp.get());
			return customerLoanApplicationRepository.save(customerLoanapp.get());

		}

		return null;

	}

	@Override
	public AllPersonalDocs updateDocument(int customerid, MultipartFile addressProof, MultipartFile panCard,
			MultipartFile incomeTax, MultipartFile addharCard, MultipartFile photo, MultipartFile signature,
			MultipartFile bankCheque, MultipartFile salarySlips) {
		CustomerLoanApplication application = customerLoanApplicationRepository.findByCustomerLoanID(customerid);
		System.out.println("hello");
		try {
			application.getAllPersonalDocument().setAddressProof(addressProof.getBytes());
			application.getAllPersonalDocument().setAddharCard(addharCard.getBytes());
			application.getAllPersonalDocument().setPanCard(panCard.getBytes());
			application.getAllPersonalDocument().setIncomeTax(incomeTax.getBytes());
			application.getAllPersonalDocument().setPhoto(photo.getBytes());
			application.getAllPersonalDocument().setSignature(signature.getBytes());
			application.getAllPersonalDocument().setBankCheque(bankCheque.getBytes());
			application.getAllPersonalDocument().setSalarySlips(salarySlips.getBytes());
			application.getAllPersonalDocument().setAddharCard(addharCard.getBytes());
			application.getAllPersonalDocument().setPanCard(panCard.getBytes());
			application.getAllPersonalDocument().setIncomeTax(incomeTax.getBytes());
			application.getAllPersonalDocument().setPhoto(photo.getBytes());
			application.getAllPersonalDocument().setSignature(signature.getBytes());
			application.getAllPersonalDocument().setBankCheque(bankCheque.getBytes());
			application.getAllPersonalDocument().setSalarySlips(salarySlips.getBytes());
			CustomerLoanApplication application2 = customerLoanApplicationRepository.save(application);
			return application2.getAllPersonalDocument();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	public CustomerLoanApplication getCustomerLoanApplication(int customerLoanID) {
		Optional<CustomerLoanApplication> custLoanApp = customerLoanApplicationRepository.findById(customerLoanID);
		if (custLoanApp.isPresent()) {
			return custLoanApp.get();
		}
		return null;
	}

	@Override
	public AllPersonalDocs getAllPersonalDocs(int documentID) {
		Optional<AllPersonalDocs> docs = allPersonalDocumentsRepository.findById(documentID);
		if (docs.isPresent()) {
			return docs.get();
		}
		return null;
	}

	@Override
	public List<CustomerLoanApplication> getAllUserAcceptedSactionedData() {
		String status = "User Sanction Accept";
		return customerLoanApplicationRepository.findAllByLoanStatus(status);
	}

	@Override
	public CustomerLoanApplication getSingleCustomerLoanApplication(int customerLoanID) {
		CustomerLoanApplication cust = customerLoanApplicationRepository.findByCustomerLoanID(customerLoanID);
		return cust;
	}

}

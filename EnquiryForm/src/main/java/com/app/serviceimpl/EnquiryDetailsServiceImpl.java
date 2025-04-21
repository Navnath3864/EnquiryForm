package com.app.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.CustomerLoanApplication;
import com.app.model.EnquiryDetails;
import com.app.repository.CustomerLoanApplicationRepository;
import com.app.repository.EnquiryDetailsRepository;
import com.app.service.EnquiryDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@Service
public class EnquiryDetailsServiceImpl implements EnquiryDetailsService {
	@Autowired
	EnquiryDetailsRepository enquiryDetailsRepository;

	@Autowired
	CustomerLoanApplicationRepository customerLoanApplicationRepository;

	@Override
	public EnquiryDetails saveDetails(@Valid String enquiryDetails, MultipartFile profilePhoto) {
		EnquiryDetails enqDetails=null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			enqDetails=objectMapper.readValue(enquiryDetails, EnquiryDetails.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		try {
			enqDetails.setProfilePhoto(profilePhoto.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return enquiryDetailsRepository.save(enqDetails);
	}
	
	@Override
	public List<EnquiryDetails> getAllEquiryDetails() {
		return enquiryDetailsRepository.findAll();
	}

	public EnquiryDetails getSingleEnquiryDetails(int customerID) {
		return enquiryDetailsRepository.findByCustomerID(customerID);
	}

	public void deleteEnquiryDetails(int customerID) {
		enquiryDetailsRepository.deleteById(customerID);
	}

	@Override
	public EnquiryDetails updateEnquiryDetails(EnquiryDetails enquiryDetails, int customerID) {
		EnquiryDetails enquiryDetails2 = enquiryDetailsRepository.findByCustomerID(customerID);
		if (enquiryDetails2 != null) {
			enquiryDetails2.setFirstName(enquiryDetails.getFirstName());
			enquiryDetails2.setLastName(enquiryDetails.getLastName());
			enquiryDetails2.setEmail(enquiryDetails.getEmail());
//			enquiryDetails2.setEnquiryStatus("cibilgenerated");
			enquiryDetails2.setEnquiryStatus(enquiryDetails.getCibilScoreData().getCibilRemark());
			enquiryDetails2.setAge(enquiryDetails.getAge());
			enquiryDetails2.setMobileNo(enquiryDetails.getMobileNo());
			enquiryDetails2.setPancardNo(enquiryDetails.getPancardNo());
			enquiryDetails2.setCibilScoreData(enquiryDetails.getCibilScoreData());
			enquiryDetailsRepository.save(enquiryDetails2);
			System.out.println(enquiryDetails2);
			return enquiryDetails2;
		}
		return null;
	}

	@Override
	public CustomerLoanApplication saveCustomerLoanApplicationForm(CustomerLoanApplication customerLoanApplication) {
//		int customer_id = customerLoanApplication.getCustomerID();
//		EnquiryDetails details = enquiryDetailsRepository.findByCustomerID(customer_id);
//		System.out.println(details);
//		String name = details.getFirstName()+details.getLastName();
//		customerLoanApplication.setCustomerName(name);
//		customerLoanApplication.setCustomerAge(details.getAge());
//		customerLoanApplication.setCustomerEmail(details.getEmail());
//		customerLoanApplication.setCustomerMobileNumber(details.getMobileNo());
//		customerLoanApplication.setCibilScoreData(details.getCibilScoreData());
//		
		CustomerLoanApplication application = customerLoanApplicationRepository.save(customerLoanApplication);

		return application;
	}

	@Override
	public EnquiryDetails updateEnquiry(EnquiryDetails enquiryDetails) {

		EnquiryDetails e = enquiryDetailsRepository.save(enquiryDetails);

		return e;
	}

	@Override
	public EnquiryDetails checkLoginUser(String email, String password) {
		List<EnquiryDetails> list = getAllEquiryDetails();
		for(EnquiryDetails user : list) {
			if(email.equals(user.getEmail()) && password.equals(user.getPassword())) {
				return user;
			}
		}

		return null;
	}

	@Override
	public boolean updateCibilStatus(int customerID,String enquiryStatus) {
		Optional<EnquiryDetails> enq = enquiryDetailsRepository.findById(customerID);
		if(enq.isPresent()) {
			enq.get().setEnquiryStatus(enquiryStatus);
			enquiryDetailsRepository.save(enq.get());
			return true;
			
		}
		return false;
	}

	@Override
	public List<EnquiryDetails> enquiryDetailsService() {
		List<EnquiryDetails> cibilPendingList = new ArrayList<>();
		 List<EnquiryDetails> list = enquiryDetailsRepository.findAll();
		 for(EnquiryDetails enquiry : list) {
			 if(enquiry.getEnquiryStatus().equals("cibilpending")) {
				 cibilPendingList.add(enquiry);
			 }
		 }
		return cibilPendingList;
	}

	

}

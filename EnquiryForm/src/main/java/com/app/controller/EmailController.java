package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.CustomerLoanApplication;
import com.app.service.EmailService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/app")
public class EmailController {
	
	@Autowired
	EmailService  emailService;
	
	@GetMapping("/api/sendSantionLetterMail/{customerLoan_ID}")
	public ResponseEntity<CustomerLoanApplication> sendSanctionLetterMail(@PathVariable int customerLoan_ID)
	{
		CustomerLoanApplication customerLoanApplication = emailService.sendSanctionLetterMailToCustomer(customerLoan_ID);
		return new ResponseEntity<CustomerLoanApplication>(customerLoanApplication,HttpStatus.OK);
		
	}
	
}

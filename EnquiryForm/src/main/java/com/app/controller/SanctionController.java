package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.CustomerLoanApplication;
import com.app.service.SanctionService;
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/app")
public class SanctionController {
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Autowired
	SanctionService sanctionService;

	
	@GetMapping("/api/genratePdf/{customerLoanID}")
	public ResponseEntity<CustomerLoanApplication> sanctionLetter(@PathVariable int customerLoanID)
	{
		CustomerLoanApplication application= sanctionService.sanctionLetter(customerLoanID);
		return new  ResponseEntity<CustomerLoanApplication>(application,HttpStatus.OK);
	}
}

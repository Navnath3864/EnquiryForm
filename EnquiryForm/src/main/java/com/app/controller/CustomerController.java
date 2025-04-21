package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



import com.app.model.CustomerLoanApplication;
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/app")
public class CustomerController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@PutMapping("/api/updateStatusByCustomer/{customer_ID}")
	public ResponseEntity<CustomerLoanApplication> updateLoanStatusofCustomerApplication(@PathVariable int customer_ID,@RequestBody CustomerLoanApplication customerLoanApplication)
	{
		String url = "http://localhost:8080/app/api/updateLoanstatus/"+customer_ID;
		restTemplate.put(url, customerLoanApplication, CustomerLoanApplication.class);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	 
}

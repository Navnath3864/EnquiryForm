package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Employee;
import com.app.service.EmployeeAuthService;

@RestController
@RequestMapping("/app")
@CrossOrigin("http://localhost:5173")
public class EmployeeAuth{
	@Autowired
	EmployeeAuthService employeeAuthService;
	
	@PostMapping("/api/employee/signup")
	public ResponseEntity<Employee> signup(@RequestPart ("employee") String emp , @RequestPart ("photo") MultipartFile profilePhoto ){
		Employee employee=employeeAuthService.signupEmployee(emp,profilePhoto);
		return new ResponseEntity<Employee>(employee ,HttpStatus.OK);
	} 
	
	@GetMapping("/api/employee/login/{email}/{password}")
	public ResponseEntity<Employee> checkLogin(@PathVariable String email, @PathVariable String password){
		
		Employee emp = employeeAuthService.checkLoginUser(email,password);
		
		return new ResponseEntity<Employee>(emp,HttpStatus.OK);
	}
}

package com.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.app.model.Employee;

public interface EmployeeAuthService {

	Employee signupEmployee(String emp, MultipartFile profilePhoto);

	Employee checkLoginUser(String email, String password);

	

}

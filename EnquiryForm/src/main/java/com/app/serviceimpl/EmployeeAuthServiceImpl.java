package com.app.serviceimpl;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.CustomerLoanApplication;
import com.app.model.Employee;
import com.app.repository.EmployeeAuthRepository;
import com.app.service.EmployeeAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeAuthServiceImpl implements EmployeeAuthService {
	@Autowired
	EmployeeAuthRepository employeeAuthRepository;
	
	@Override
	public Employee signupEmployee(String emp, MultipartFile profilePhoto) {
		ObjectMapper objectMapper = new ObjectMapper();
		Employee employee=null;
		
		try {
			employee = objectMapper.readValue(emp , Employee.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		try {
		employee.setProfilePhoto(profilePhoto.getBytes());
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		return employeeAuthRepository.save(employee);
	}

	
	@Override
	public Employee checkLoginUser(String email, String password) {
		List<Employee> list = employeeAuthRepository.findAll();
		for (Employee emp : list) {
			if (emp.getEmailId().equals(email) && emp.getPassword().equals(password)) {
				return emp;
			}

		}
		return null;
	}


	

}

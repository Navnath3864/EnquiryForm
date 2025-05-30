package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Employee;
@Repository
public interface EmployeeAuthRepository extends JpaRepository<Employee, Integer>{

}

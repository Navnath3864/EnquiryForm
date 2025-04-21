package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class DependentInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dependentInfoId;

	private int noOfFamilyMember;

	private int noOfChild;

	private String maritalStatus;

	private String dependentMember;

	private double familyIncome;

}
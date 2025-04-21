package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class GuarantorDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int guarantorId;

	private String guarantorName;

	private String guarantorDateOfBirth;
	private String guarantorRelationshipwithCustomer;

	private long guarantorMobileNumber;

	private long guarantorAdharCardNo;
	private String guarantorMortgageDetails;
	private String guarantorJobDetails;

	private String guarantorLocalAddress;
	private String guarantorPermanentAddress;

}
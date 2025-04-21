package com.app.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class SanctionLetter {
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int sanctionId;
private	Date sanctionDate;
private	String applicantName;
private	Double contactDetails;
private	String producthomeEquity;
private	Double loanAmtSanctioned;
private	String interestType;
private	float rateOfInterest;
private	int loanTenureInMonth;
private	Double monthlyEmiAmount;
private	String modeOfPayment;
private	String remarks;
private	String termsCondition;
private	String status;

@Lob
@Column(length = 9000000)
private byte[] sanctionLetter;

}

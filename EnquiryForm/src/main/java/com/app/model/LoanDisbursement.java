package com.app.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class LoanDisbursement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int agreementid;
	private int loanNo;
	private String agreementDate;
	private String amountPayType;
	private double totalAmount;
	private String bankName;
	private long accountNumber;
	private String ifsccode;
	private String accountType;
	private double transferAmount;
	private String paymentStatus;
	private String amountPaidDate;
}


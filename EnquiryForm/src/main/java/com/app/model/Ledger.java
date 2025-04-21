package com.app.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ledgerId;
	private LocalDate ledgerCreatedDate;
	private double totalLoanAmount;
	private double payableAmountwithInterest;
	private int tenure;
	private double monthlyEMI;
	private double amountPaidtillDate;
	private double remainingAmount;
	private LocalDate nextEmiDatestart;
	private LocalDate nextEmiDateEnd;
	private int defaulterCount;
	private String previousEmitStatus;
	private String currentMonthEmiStatus;
	private LocalDate loanEndDate;
	private String loanStatus;
	


}


package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;

	private String accounType;

	private double accountBalance;

	private String accountHolderName;

	private String accountStatus;

	private long accountNumber;

}

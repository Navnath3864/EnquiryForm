package com.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class EnquiryDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerID;
	
	@NotBlank(message = "First Name is required")
	@Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Name must start with a capital letter and contain only alphabets")
	private String firstName;
	 
	@NotBlank(message = "Last Name is required")
	@Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Name must start with a capital letter and contain only alphabets")
	private String lastName;
	
	@Min(value = 18, message = "Rquired Age at Least 18 Years")
	@NotNull(message = "Age is required to fill")
	private int age;
	
	@Email(message = "Invalid Email Id")
	private String email;
	
	private String password;
	
	@Min(value = 1000000000, message = "Mobile number must be at least 10 digits")
	@Max(value = 9999999999L, message = "Mobile number must be at most 10 digits")
	private long mobileNo;
	
	private String enquiryStatus=null;
	
	@NotBlank(message = "PancardNo is required")
	@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN card number. Format: AAAAA1234A")
	private String pancardNo;
	
	private String role;
	
	@Lob
	private byte[] profilePhoto;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cibil_id")
	private CibilScoreData cibilScoreData;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerLoanApplication customerLoanApplication;
	

}

package com.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import lombok.Data;

@Entity
@Data
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerAddressId;

	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	private PermanentAddress permanentAddress;

	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	private LocalAddress localAddress;

}

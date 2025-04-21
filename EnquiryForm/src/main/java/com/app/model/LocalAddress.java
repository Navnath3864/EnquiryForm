package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LocalAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int localAddressId;

	private String areaName;
	private String cityName;
	private String district;
	private String state;
	private long pincode;
	private int houseNumber;
	private String streetName;

}
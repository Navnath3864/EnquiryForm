package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class AllPersonalDocs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int documentID;

	@Lob
	@Column(length = 999999999)
	private byte[] addressProof;

	@Lob
	@Column(length = 999999999)
	private byte[] panCard;

	@Lob
	@Column(length = 999999999)
	private byte[] incomeTax;

	@Lob
	@Column(length = 999999999)
	private byte[] addharCard;

	@Lob
	@Column(length = 999999999)
	private byte[] photo;

	@Lob
	@Column(length = 999999999)
	private byte[] signature;

	@Lob
	@Column(length = 999999999)
	private byte[] bankCheque;

	@Lob
	@Column(length = 999999999)
	private byte[] salarySlips;

}

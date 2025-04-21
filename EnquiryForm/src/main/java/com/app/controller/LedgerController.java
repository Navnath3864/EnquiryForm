package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Ledger;
import com.app.service.LedgerService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/app")
public class LedgerController {

	@Autowired
	LedgerService ledgerService;

	@GetMapping("/api/saveledgerdata/{customerLoanID}")
	public ResponseEntity<List<Ledger>> ledgerData(@PathVariable int customerLoanID) {
		List<Ledger> ledgerData = ledgerService.saveLedgerData(customerLoanID);
		return new ResponseEntity<List<Ledger>>(ledgerData, HttpStatus.OK);
	}

	@GetMapping("/api/getLadgerdata/{customerLoanID}")
	public ResponseEntity<List<Ledger>> getledgerData(@PathVariable int customerLoanID) {
		List<Ledger> ledgerData = ledgerService.getLedgerData(customerLoanID);
		return new ResponseEntity<List<Ledger>>(ledgerData, HttpStatus.OK);
	}

	@PutMapping("/api/updateledgerdata/{id}/{option}/{customerLoanId}")
	public ResponseEntity<Ledger> saveledger(@PathVariable int id, @PathVariable String option,@PathVariable int customerLoanId) {
		Ledger ledger = ledgerService.updateledger(id, option , customerLoanId);
		return new ResponseEntity<Ledger>(ledger, HttpStatus.ACCEPTED);
	}

}

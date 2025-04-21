package com.app.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.CustomerLoanApplication;
import com.app.model.Ledger;
import com.app.repository.CustomerLoanApplicationRepository;
import com.app.repository.LedgerRepository;
import com.app.service.EmailService;
import com.app.service.LedgerService;

@Service
public class LedgerServiceImpl implements LedgerService {

	@Autowired
	LedgerRepository ledgerRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	CustomerLoanApplicationServiceImpl customerLoanApplicationServiceImpl;

	@Autowired
	CustomerLoanApplicationRepository customerLoanApplicationRepository;

	/*
	 * @Override public List<Ledger> saveLedgerData(Ledger ledger1, int
	 * customerLoanID) { CustomerLoanApplication custApplication =
	 * customerLoanApplicationServiceImpl
	 * .getCustomerLoanApplication(customerLoanID); int count = 0; List<Ledger>
	 * ledgers = custApplication.getLedger();
	 * 
	 * while (count < custApplication.getRequiredTenure()) { Ledger ledger = new
	 * Ledger(); ledger.setNextEmiDatestart(ledger1.getNextEmiDatestart());
	 * ledger.setNextEmiDateEnd(ledger1.getNextEmiDateEnd());
	 * ledger.setLoanEndDate(ledger.getLoanEndDate());
	 * ledger.setTotalLoanAmount(custApplication.getLoandisbursement().
	 * getTotalAmount()); ledger.setTenure(custApplication.getRequiredTenure());
	 * ledger.setPayableAmountwithInterest(ledger.getPayableAmountwithInterest());
	 * ledger.setMonthlyEMI(ledger.getTotalLoanAmount() / ledger.getTenure());
	 * ledger.setLoanStatus("pending"); ledgers.add(ledger); count++; }
	 * 
	 * ledgerRepository.saveAll(ledgers);
	 * custApplication.getLedger().addAll(ledgers); List<Ledger> al =
	 * custApplication.getLedger(); return al; }
	 * 
	 */
	@Override
	public List<Ledger> saveLedgerData(int customerLoanID) {
		CustomerLoanApplication custApplication = customerLoanApplicationServiceImpl
				.getCustomerLoanApplication(customerLoanID);
		// System.out.println(custApplication.getCustomerTotalLoanRequired());

		List<Ledger> ledgers = new ArrayList<>();
		Double totalLoanAmount = custApplication.getCustomerTotalLoanRequired();
		int tenure = custApplication.getRequiredTenure();
		Double monthlyEMI = totalLoanAmount / tenure;

		LocalDate emiStartDate = LocalDate.now();
		LocalDate emiEndDate = LocalDate.now();

		for (int count = 1; count <= tenure; count++) {
			Ledger ledger = new Ledger();
			ledger.setLedgerCreatedDate(emiStartDate);
			ledger.setNextEmiDatestart(emiEndDate);
			ledger.setNextEmiDateEnd(emiEndDate);

			ledger.setLoanEndDate(emiEndDate.plusMonths(tenure - count)); // You can update this as per actual logic
			ledger.setTotalLoanAmount(totalLoanAmount);
			ledger.setTenure(tenure);
			ledger.setPayableAmountwithInterest(totalLoanAmount + (totalLoanAmount * 0.1)); // Assuming 10% interest,
																							// update as needed
			ledger.setMonthlyEMI(monthlyEMI);
			ledger.setLoanStatus("Pending");
			ledger.setDefaulterCount(count);

			ledgers.add(ledger);

			// Move EMI start and end to next month
			emiStartDate = emiStartDate.plusMonths(1);
			emiEndDate = emiEndDate.plusMonths(1);
		}

		// ledgerRepository.saveAll(ledgers);
		// custApplication.getLedger().addAll(ledgers);
		custApplication.setLedger(ledgers);
		customerLoanApplicationRepository.save(custApplication);
		return custApplication.getLedger();
	}

	@Override
	public List<Ledger> getLedgerData(int customerLoanID) {

		CustomerLoanApplication custApplication = customerLoanApplicationServiceImpl
				.getCustomerLoanApplication(customerLoanID);
		System.out.println(custApplication.getLedger());
		return custApplication.getLedger();
	}

	@Override
	public Ledger updateledger(int id, String option, int customerLoanId) {
		Optional<Ledger> optionalCurrent = ledgerRepository.findById(id);
		Optional<Ledger> optionalPrevious = ledgerRepository.findById(id - 1);

		if (optionalCurrent.isEmpty()) {
			throw new RuntimeException("Ledger entry with id " + id + " not found.");
		}

		Ledger current = optionalCurrent.get();

		if ("pay".equalsIgnoreCase(option)) {
			double emi = Optional.ofNullable(current.getMonthlyEMI()).orElse(0.0);

			if (optionalPrevious.isEmpty()) {
				// First EMI 
				double totalLoan =Optional.ofNullable(current.getTotalLoanAmount()).orElse(0.0);
				current.setRemainingAmount(totalLoan - emi);
				current.setAmountPaidtillDate(emi);
				current.setPreviousEmitStatus("unpaid");
			} else {
				Ledger previous = optionalPrevious.get();
				double prevRemaining = Optional.ofNullable(previous.getRemainingAmount()).orElse(0.0);
				double prevPaid = Optional.ofNullable(previous.getAmountPaidtillDate()).orElse(0.0);

				current.setRemainingAmount(prevRemaining - emi);
				current.setAmountPaidtillDate(prevPaid + emi);
				current.setPreviousEmitStatus(previous.getCurrentMonthEmiStatus());
			}

			current.setCurrentMonthEmiStatus("paid");
			current.setMonthlyEMI(0.0);

			if (optionalCurrent.get().getRemainingAmount()<=0) {
				current.setLoanStatus("nill");
			}

			ledgerRepository.save(current);
			emailService.sentLegderStatusToCustomerMail(customerLoanId, current);
			return current;
		}

		if ("skip".equalsIgnoreCase(option)) {
			Optional<Ledger> optionalNext = ledgerRepository.findById(id + 1);

			if (optionalNext.isEmpty()) {
				throw new RuntimeException("Next ledger entry not found for skipping EMI.");
			}

			Ledger next = optionalNext.get();
			double currentEmi = Optional.ofNullable(current.getMonthlyEMI()).orElse(0.0);
			double nextEmi = Optional.ofNullable(next.getMonthlyEMI()).orElse(0.0);

			next.setMonthlyEMI(currentEmi + nextEmi);
			current.setCurrentMonthEmiStatus("unpaid");

			ledgerRepository.save(next);
			ledgerRepository.save(current);
			emailService.sentLegderStatusToCustomerMail(customerLoanId, current);
			return current;
		}

		throw new IllegalArgumentException("Invalid option: " + option);
	}

}

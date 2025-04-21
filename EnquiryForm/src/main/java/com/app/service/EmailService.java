package com.app.service;

import com.app.model.CustomerLoanApplication;
import com.app.model.Ledger;

public interface EmailService {

	CustomerLoanApplication sendSanctionLetterMailToCustomer(int customer_ID);
	
	void sentLegderStatusToCustomerMail(int customerloanId,Ledger ledger );

}

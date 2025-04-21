package com.app.service;

import com.app.model.CustomerLoanApplication;

public interface SanctionService {

	CustomerLoanApplication sanctionLetter(int customerLoanID);

}

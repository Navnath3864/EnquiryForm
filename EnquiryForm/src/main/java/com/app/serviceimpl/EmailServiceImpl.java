package com.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.app.model.CustomerLoanApplication;
import com.app.model.Ledger;
import com.app.repository.CustomerLoanApplicationRepository;
import com.app.service.EmailService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    CustomerLoanApplicationRepository applicationRepository;

    @Autowired
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public CustomerLoanApplication sendSanctionLetterMailToCustomer(int customerLoan_ID) {

        MimeMessage mimemessage = sender.createMimeMessage();

        CustomerLoanApplication customerLoanApplication = applicationRepository.findByCustomerLoanID(customerLoan_ID);

        byte[] sanctionLetter = null;
        if (customerLoanApplication.getSanctionLetter() != null) {
            sanctionLetter = customerLoanApplication.getSanctionLetter().getSanctionLetter();
        }

        if (sanctionLetter == null || sanctionLetter.length == 0) {
            System.out.println("Sanction letter is null or empty. Email not sent.");
            return null; // or throw an exception, based on your use case
        }

        try {
            MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimemessage, true);
            mimemessageHelper.setFrom(fromEmail);
        //    System.out.println("Email ===>"+customerLoanApplication.getCustomerEmail());
            mimemessageHelper.setTo(customerLoanApplication.getCustomerEmail());
            mimemessageHelper.setSubject("Happy Finance Ltd. Sanction Letter");

            String text = "Dear " + customerLoanApplication.getCustomerName() + ",\n\n"
                    + "This letter is to inform you that we have reviewed your request for a credit loan. "
                    + "We are pleased to offer you a credit loan of "
                    + customerLoanApplication.getSanctionLetter().getLoanAmtSanctioned() + " for "
                    + customerLoanApplication.getSanctionLetter().getLoanTenureInMonth() + " months.\n\n"
                    + "We are confident that you will manage your credit loan responsibly, and we look forward to your continued business.\n\n"
                    + "Should you have any questions about your credit loan, please do not hesitate to contact us.\n\n"
                    + "Thank you for your interest in our services.";

            mimemessageHelper.setText(text);

            // Attach only if byte array is valid
            mimemessageHelper.addAttachment("loanSanctionLetter.pdf", new ByteArrayResource(sanctionLetter));

            sender.send(mimemessage);
        } catch (MessagingException e) {
            System.out.println("Email Failed to Send!");
            e.printStackTrace();
        }

        return customerLoanApplication;
    }

	@Override
	public void sentLegderStatusToCustomerMail(int customerloanId, Ledger ledger  ) {
		CustomerLoanApplication application = applicationRepository.findByCustomerLoanID(customerloanId);
			SimpleMailMessage simple = new SimpleMailMessage();
			simple.setTo(application.getCustomerEmail());
			simple.setFrom(fromEmail);
			simple.setText("Amount Paid Till Date: "+ledger.getAmountPaidtillDate()+"\n"+"RemainingAmount: "+ledger.getRemainingAmount());
			simple.setSubject("Ledger Deatils");
			
			sender.send(simple);		
	}

}

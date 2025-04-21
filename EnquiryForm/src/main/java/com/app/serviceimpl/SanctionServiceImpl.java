package com.app.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.CustomerLoanApplication;
import com.app.model.SanctionLetter;
import com.app.repository.CustomerLoanApplicationRepository;
import com.app.service.SanctionService;

@Service
public class SanctionServiceImpl implements SanctionService {

	@Autowired
	CustomerLoanApplicationRepository applicationRepository;
	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Override
	public CustomerLoanApplication sanctionLetter(int customerLoanID) {
		Date date = new Date();

		CustomerLoanApplication customerLoanApplication = applicationRepository.findByCustomerLoanID(customerLoanID);
		if (customerLoanApplication != null) {
		//	if (customerLoanApplication.getSanctionLetter() == null) {
			//	customerLoanApplication.setSanctionLetter(new SanctionLetter());
		//	}

			customerLoanApplication.getSanctionLetter().setSanctionDate(date);
			customerLoanApplication.getSanctionLetter().setApplicantName(customerLoanApplication.getCustomerName());
			customerLoanApplication.getSanctionLetter().setContactDetails(customerLoanApplication.getCustomerMobileNumber());
			customerLoanApplication.getSanctionLetter().setProducthomeEquity("Home Loan");
			customerLoanApplication.getSanctionLetter().setLoanAmtSanctioned(customerLoanApplication.getCustomerTotalLoanRequired());
			customerLoanApplication.getSanctionLetter().setInterestType("Fixed");
			customerLoanApplication.getSanctionLetter().setRateOfInterest(7.5f);
			customerLoanApplication.getSanctionLetter().setLoanTenureInMonth(customerLoanApplication.getRequiredTenure());
			customerLoanApplication.getSanctionLetter().setMonthlyEmiAmount(customerLoanApplication.getCustomerTotalLoanRequired()/customerLoanApplication.getRequiredTenure());
			customerLoanApplication.getSanctionLetter().setModeOfPayment("Bank Transfer");
			customerLoanApplication.getSanctionLetter().setRemarks("Loan sanctioned successfully");
			customerLoanApplication.getSanctionLetter().setTermsCondition("The loan must be repaid within the tenure period.");
			customerLoanApplication.getSanctionLetter().setStatus(customerLoanApplication.getLoanStatus());

			Document document = new Document(PageSize.A4);

			String title = "Happy Finance Ltd.";
			String content1 = "\n\n Dear " + customerLoanApplication.getCustomerName() + ","
					+ "\nABC Finance Ltd. is Happy to informed you that your loan application has been approved. ";

			String content2 = "\n\nWe hope that you find the terms and conditions of this loan satisfactory "
					+ "and that it will help you meet your financial needs.\n\nIf you have any questions or need any assistance regarding your loan, "
					+ "please do not hesitate to contact us.\n\nWe wish you all the best and thank you for choosing us."
					+ "\n\nSincerely,\n\n" + "ABC XYZ (Credit Manager)";

			ByteArrayOutputStream opt = new ByteArrayOutputStream();

			PdfWriter.getInstance(document, opt);
			document.open();

			Image img = null;
			try {
				img = Image.getInstance("C:/Users/lenovo/Desktop/neoli.jpg");
				img.scalePercent(50, 50);
				img.setAlignment(Element.ALIGN_RIGHT);
				document.add(img);

			} catch (BadElementException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Font titlefont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
			Paragraph titlepara = new Paragraph(title, titlefont);
			titlepara.setAlignment(Element.ALIGN_CENTER);
			document.add(titlepara);

			Font titlefont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
			Paragraph paracontent1 = new Paragraph(content1, titlefont2);
			document.add(paracontent1);

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100f);
			table.setWidths(new int[] { 2, 2 });
			table.setSpacingBefore(10);

			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.WHITE);
			cell.setPadding(5);

			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			font.setColor(5, 5, 161);

			Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
			font.setColor(5, 5, 161);

			cell.setPhrase(new Phrase("Loan amount Sanctioned", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(
					String.valueOf("₹ " + customerLoanApplication.getSanctionLetter().getLoanAmtSanctioned()), font1));
			table.addCell(cell);

			cell.setPhrase(new Phrase("loan tenure", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(
					String.valueOf(" "+ customerLoanApplication.getSanctionLetter().getLoanTenureInMonth()), font1));
			table.addCell(cell);


			cell.setPhrase(new Phrase("interest rate", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(
					String.valueOf(" " + customerLoanApplication.getSanctionLetter().getRateOfInterest()), font1));
			table.addCell(cell);


			cell.setPhrase(new Phrase("Sanction letter generated Date", font));
			table.addCell(cell);

			cell.setPhrase(
					new Phrase(String.valueOf(customerLoanApplication.getSanctionLetter().getSanctionDate()), font1));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Total loan Amount with Intrest", font));
			table.addCell(cell);

			document.add(table);

			Font titlefont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
			Paragraph paracontent2 = new Paragraph(content2, titlefont3);
			document.add(paracontent2);
			document.close();

			ByteArrayInputStream byt = new ByteArrayInputStream(opt.toByteArray());
			byte[] bytes = byt.readAllBytes();
			customerLoanApplication.getSanctionLetter().setSanctionLetter(bytes);
			emailServiceImpl.sendSanctionLetterMailToCustomer(customerLoanID);
			return applicationRepository.save(customerLoanApplication);
		} else {
			return null;
		}
	}

}

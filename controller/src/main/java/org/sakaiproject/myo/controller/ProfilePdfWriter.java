package org.sakaiproject.myo.controller;

import java.awt.Color;
import java.io.OutputStream;

import org.sakaiproject.myo.entity.OkrUserProfile;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class ProfilePdfWriter {
	public static void writeProfileToPdf(OkrUserProfile user, OutputStream outputStream) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
		document.open();
		Font boldFontlarge = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);

		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
		Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
		Font boldFontsmall = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

		LineSeparator lineSeparator = new LineSeparator();
		lineSeparator.setLineWidth(2f);
		
		PdfPTable titleTable = new PdfPTable(1);

		PdfPCell titlenameLabelCell = new PdfPCell(new Phrase(user.getDisplay_name(), boldFontlarge));
		titlenameLabelCell.setBorder(Rectangle.NO_BORDER);
		titlenameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER); 

		titleTable.addCell(titlenameLabelCell);
		
		titleTable.completeRow();

		document.add(titleTable);


		// Group 1: Basic Information and Summary
		PdfPTable basicInfoTable = new PdfPTable(1);

		PdfPCell nameLabelCell = new PdfPCell(new Phrase("Basic Information", boldFont));
		nameLabelCell.setBorder(Rectangle.NO_BORDER);
		nameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER); 

		basicInfoTable.addCell(nameLabelCell);


		basicInfoTable.completeRow();

		document.add(basicInfoTable);
        document.add(new Chunk(lineSeparator));
		document.add(new Paragraph());


        Phrase nameEmailPhrase = new Phrase();



        nameEmailPhrase.add(new Chunk("Email: ", normalFont));
        nameEmailPhrase.add(new Chunk(user.getLast_modified_by(), normalFont));
        
        nameEmailPhrase.add(new Chunk("  |  ", normalFont));
        nameEmailPhrase.add(new Chunk("Phone: " + user.getMobile(), normalFont));
        nameEmailPhrase.add(new Chunk("  |  ", normalFont));

        nameEmailPhrase.add(new Chunk("Address: ", normalFont));
        nameEmailPhrase.add(new Chunk(user.getPostal_address(), normalFont));



        document.add(nameEmailPhrase);
document.add(new Paragraph("Summary: ", boldFontsmall));
		document.add(new Paragraph(user.getDescription(), normalFont));






		// Group 2: Education
		PdfPTable educationTable = new PdfPTable(1);

		PdfPCell educationLabelCell = new PdfPCell(new Phrase("Education", boldFont));
		educationLabelCell.setBorder(Rectangle.NO_BORDER);
		educationLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		educationTable.addCell(educationLabelCell);

		document.add(educationTable);
        document.add(new Chunk(lineSeparator));
		document.add(new Paragraph(user.getOrganization(), boldFontsmall));
		document.add(new Paragraph(user.getEducation_subjects(), normalFont));
		document.add(new Paragraph("Courses", boldFontsmall));

		document.add(new Paragraph(user.getEducation_course(), normalFont));
	


        
        
        



		// Group 3: Experience
		PdfPTable honorsTable = new PdfPTable(1);

		PdfPCell honorsLabelCell = new PdfPCell(new Phrase("Experience", boldFont));
		honorsLabelCell.setBorder(Rectangle.NO_BORDER);
		honorsLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER); 

		honorsTable.addCell(honorsLabelCell);


		document.add(honorsTable);
        document.add(new Chunk(lineSeparator));
        
		document.add(new Paragraph("Publications and conference", boldFontsmall));
		document.add(new Paragraph(user.getPublications(), normalFont));
		document.add(new Paragraph("Academic/research", boldFontsmall));
		document.add(new Paragraph(user.getAcademic_profile_url(), normalFont));



		// Group 4: OKR
		PdfPTable okrTable = new PdfPTable(1);

		PdfPCell okrLabelCell = new PdfPCell(new Phrase("OKR", boldFont));
		okrLabelCell.setBorder(Rectangle.NO_BORDER);
		okrLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER); 

		okrTable.addCell(okrLabelCell);


		document.add(okrTable);
        document.add(new Chunk(lineSeparator));


		document.close();
	}
}

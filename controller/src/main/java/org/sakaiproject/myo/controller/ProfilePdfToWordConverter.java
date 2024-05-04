package org.sakaiproject.myo.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPBdr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.sakaiproject.myo.entity.OkrUserProfile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ProfilePdfToWordConverter {
	public static void writeProfileToWord(OkrUserProfile user, String filePath) {
		try {
		    XWPFDocument document = new XWPFDocument();
		    FileOutputStream out = new FileOutputStream(filePath);

		    XWPFParagraph title = document.createParagraph();
		    title.setAlignment(ParagraphAlignment.CENTER);
		    XWPFRun titleRun = title.createRun();
		    titleRun.setText(user.getDisplay_name());
		    titleRun.setBold(true);
		    titleRun.setFontSize(20);

		    XWPFParagraph basicInfoTitle = document.createParagraph();
		    basicInfoTitle.setAlignment(ParagraphAlignment.CENTER);
		    XWPFRun basicInfoTitleRun = basicInfoTitle.createRun();
		    basicInfoTitleRun.setText("Basic Information");
		    basicInfoTitleRun.setBold(true);
		    
		    basicInfoTitle.setBorderBottom(Borders.SINGLE);
		    CTPBdr border1 = basicInfoTitle.getCTP().getPPr().getPBdr();
		    if (border1 == null) border1 = basicInfoTitle.getCTP().getPPr().addNewPBdr();
		    CTBorder borderBottom1 = border1.addNewBottom();
		    borderBottom1.setVal(STBorder.SINGLE);
		    borderBottom1.setColor("000000"); 

		    XWPFParagraph basicInfo = document.createParagraph();
		    XWPFRun basicInfoRun = basicInfo.createRun();
		    basicInfoRun.setText("Email: " + user.getLast_modified_by() + " | Phone: " + user.getMobile() + " | Address: " + user.getPostal_address());
		    basicInfoRun.setFontSize(12);
		    

		    XWPFParagraph summaryTitle = document.createParagraph();
		    summaryTitle.setAlignment(ParagraphAlignment.LEFT);
		    XWPFRun summaryTitleRun = summaryTitle.createRun();
		    summaryTitleRun.setText("Summary");
		    summaryTitleRun.setBold(true);

		    XWPFParagraph summary = document.createParagraph();
		    XWPFRun summaryRun = summary.createRun();
		    summaryRun.setText(user.getDescription());

		    document.createParagraph().createRun().addBreak(BreakType.TEXT_WRAPPING);

		    XWPFParagraph educationTitle = document.createParagraph();
		    educationTitle.setAlignment(ParagraphAlignment.CENTER);
		    XWPFRun educationTitleRun = educationTitle.createRun();
		    educationTitleRun.setText("Education");
		    educationTitleRun.setBold(true);
		    
		    educationTitle.setBorderBottom(Borders.SINGLE);
		    CTPBdr border2 = educationTitle.getCTP().getPPr().getPBdr();
		    if (border2 == null) border2 = educationTitle.getCTP().getPPr().addNewPBdr();
		    CTBorder borderBottom2 = border2.addNewBottom();
		    borderBottom2.setVal(STBorder.SINGLE);
		    borderBottom2.setColor("000000"); 

		    XWPFParagraph educationDetails = document.createParagraph();
		    XWPFRun educationDetailsRun = educationDetails.createRun();
		    educationDetailsRun.setText(user.getOrganization() + "\n" + user.getEducation_subjects());
		    educationDetailsRun.addBreak(BreakType.TEXT_WRAPPING);
		    educationDetailsRun.setText("Courses: " + user.getEducation_course());

		    document.createParagraph().createRun().addBreak(BreakType.TEXT_WRAPPING);


			XWPFParagraph experienceTitle = document.createParagraph();
			experienceTitle.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun experienceTitleRun = experienceTitle.createRun();
			experienceTitleRun.setText("Experience");
			experienceTitleRun.setBold(true);
	    
			experienceTitle.setBorderBottom(Borders.SINGLE);
		    CTPBdr border3 = experienceTitle.getCTP().getPPr().getPBdr();
		    if (border3 == null) border3 = experienceTitle.getCTP().getPPr().addNewPBdr();
		    CTBorder borderBottom3 = border3.addNewBottom();
		    borderBottom3.setVal(STBorder.SINGLE);
		    borderBottom3.setColor("000000"); 
		    
			XWPFParagraph experienceDetails = document.createParagraph();
			XWPFRun experienceDetailsRun = experienceDetails.createRun();
			experienceDetailsRun.setText("Publications and conference: " + user.getPublications());
			experienceDetailsRun.addBreak(BreakType.TEXT_WRAPPING);
			experienceDetailsRun.setText("Academic/research: " + user.getAcademic_profile_url());

			document.createParagraph().createRun().addBreak(BreakType.TEXT_WRAPPING);

			XWPFParagraph okrTitle = document.createParagraph();
			okrTitle.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun okrTitleRun = okrTitle.createRun();
			okrTitleRun.setText("OKR");
			okrTitleRun.setBold(true);
			okrTitle.setBorderBottom(Borders.SINGLE);
		    CTPBdr border4 = okrTitle.getCTP().getPPr().getPBdr();
		    if (border4 == null) border4 = okrTitle.getCTP().getPPr().addNewPBdr();
		    CTBorder borderBottom4 = border4.addNewBottom();
		    borderBottom4.setVal(STBorder.SINGLE);
		    borderBottom4.setColor("000000"); 
		    
			document.write(out);
			out.close();
			document.close();
			System.out.println("Document created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

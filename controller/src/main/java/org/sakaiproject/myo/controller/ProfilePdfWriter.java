package org.sakaiproject.myo.controller;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;

import org.sakaiproject.myo.entity.OkrUserProfile;
public class ProfilePdfWriter {
    public static void writeProfileToPdf(OkrUserProfile user, OutputStream outputStream) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        BaseColor myCustomColor = new BaseColor(25,82,58); 
        BaseColor grayColor = new BaseColor(168, 168, 168); 
        Font boldGreenFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, myCustomColor);

        PdfPTable table = new PdfPTable(1); 

        PdfPCell nameCell = new PdfPCell(new Phrase("Name:", boldGreenFont));
        nameCell.setBackgroundColor(grayColor); 
        nameCell.setBorder(Rectangle.NO_BORDER); 

        table.addCell(nameCell);

        PdfPCell nameValueCell = new PdfPCell(new Phrase(user.getNickname()));
        nameValueCell.setBorder(Rectangle.NO_BORDER); 

        table.addCell(nameValueCell);

        table.completeRow();

        document.add(table);
        

        document.add(new Paragraph("Email: " + user.getLast_modified_by(), boldGreenFont));

        document.close();
    }

}

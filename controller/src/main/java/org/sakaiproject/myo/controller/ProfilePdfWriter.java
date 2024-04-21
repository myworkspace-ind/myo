package org.sakaiproject.myo.controller;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;

import org.sakaiproject.myo.entity.OkrUserProfile;
public class ProfilePdfWriter {
    public static void writeProfileToPdf(OkrUserProfile user, OutputStream outputStream) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Name: " + user.getNickname()));
        document.add(new Paragraph("Email: " + user.getLast_modified_by()));

        document.close();
    }

}

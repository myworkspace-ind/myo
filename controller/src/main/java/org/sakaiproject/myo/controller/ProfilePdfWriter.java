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

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;

public class ProfilePdfWriter {

    public static void writeProfileToPdf(String userDataJson, OutputStream outputStream) throws Exception {
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

        PdfPCell titleNameLabelCell = new PdfPCell(new Phrase("Profile", boldFontlarge));
        titleNameLabelCell.setBorder(Rectangle.NO_BORDER);
        titleNameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        titleTable.addCell(titleNameLabelCell);

        titleTable.completeRow();

        document.add(titleTable);

        JSONObject user = new JSONObject(userDataJson).getJSONObject("data");

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

        if (user.has("email") && !user.isNull("email")) {
            nameEmailPhrase.add(new Chunk("Email: ", normalFont));
            nameEmailPhrase.add(new Chunk(user.getString("email"), normalFont));
        }

        if (user.has("username") && !user.isNull("username")) {
            nameEmailPhrase.add(new Chunk("  |  ", normalFont));
            nameEmailPhrase.add(new Chunk("username: " + user.getString("username"), normalFont));
        }
        if (user.has("userRole") && !user.isNull("userRole")) {
            nameEmailPhrase.add(new Chunk("  |  ", normalFont));
            nameEmailPhrase.add(new Chunk("userRole: " + user.getString("userRole"), normalFont));
        }
        if (user.has("telephone") && !user.isNull("telephone")) {
            nameEmailPhrase.add(new Chunk("  |  ", normalFont));
            nameEmailPhrase.add(new Chunk("Phone: " + user.getString("telephone"), normalFont));
        }

        if (user.has("address") && !user.isNull("address")) {
            nameEmailPhrase.add(new Chunk("  |  ", normalFont));
            nameEmailPhrase.add(new Chunk("Address: " + user.getString("address"), normalFont));
        }

        document.add(nameEmailPhrase);

        if (user.has("birthday") && !user.isNull("birthday")) {
            document.add(new Paragraph("Birthday: ", boldFontsmall));
            document.add(new Paragraph(user.getString("birthday"), normalFont));
        }

        if (user.has("orgs") && user.getJSONArray("orgs").length() > 0) {
            PdfPTable orgTable = new PdfPTable(1);
            PdfPCell orgLabelCell = new PdfPCell(new Phrase("Organization", boldFont));
            orgLabelCell.setBorder(Rectangle.NO_BORDER);
            orgLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            orgTable.addCell(orgLabelCell);
            document.add(orgTable);
            document.add(new Chunk(lineSeparator));

            JSONArray orgs = user.getJSONArray("orgs");
            for (int i = 0; i < orgs.length(); i++) {
                JSONObject org = orgs.getJSONObject(i);
                if (org.has("name") && !org.isNull("name")) {
                    document.add(new Paragraph("Org Name: ", boldFontsmall));
                    document.add(new Paragraph(org.getString("name"), normalFont));
                }
            }
        }

        document.close();
    }
}

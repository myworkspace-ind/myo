package org.sakaiproject.myo.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStream;

public class ProfilePdfWriter {

    public static void writeProfileToPdf(String userDataJson, OutputStream outputStream) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font boldFontLarge = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        Font boldFontSmall = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        Font normalFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineWidth(1f);

        PdfPTable titleTable = new PdfPTable(1);
        PdfPCell titleNameLabelCell = new PdfPCell(new Phrase("CV", boldFontLarge));
        titleNameLabelCell.setBorder(Rectangle.NO_BORDER);
        titleNameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleNameLabelCell.setPaddingBottom(20f);
        titleTable.addCell(titleNameLabelCell);
        titleTable.completeRow();
        document.add(titleTable);

        JSONObject user = new JSONObject(userDataJson).getJSONObject("data");
        PdfPTable basicInfoTable = new PdfPTable(1);
        PdfPCell nameLabelCell = new PdfPCell(new Phrase("Basic Information", boldFont));
        nameLabelCell.setBorder(Rectangle.NO_BORDER);
        nameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameLabelCell.setPaddingBottom(10f);
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
            nameEmailPhrase.add(new Chunk("Username: " + user.getString("username"), normalFont));
        }
        if (user.has("userRole") && !user.isNull("userRole")) {
            nameEmailPhrase.add(new Chunk("  |  ", normalFont));
            nameEmailPhrase.add(new Chunk("Role: " + user.getString("userRole"), normalFont));
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
            document.add(new Paragraph("Birthday: ", boldFontSmall));
            document.add(new Paragraph(user.getString("birthday"), normalFont));
        }

        if (user.has("orgs") && user.getJSONArray("orgs").length() > 0) {
            PdfPTable orgTable = new PdfPTable(1);
            PdfPCell orgLabelCell = new PdfPCell(new Phrase("Organization", boldFont));
            orgLabelCell.setBorder(Rectangle.NO_BORDER);
            orgLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            orgLabelCell.setPaddingBottom(10f);
            orgTable.addCell(orgLabelCell);
            document.add(orgTable);
            document.add(new Chunk(lineSeparator));

            JSONArray orgs = user.getJSONArray("orgs");
            for (int i = 0; i < orgs.length(); i++) {
                JSONObject org = orgs.getJSONObject(i);
                if (org.has("name") && !org.isNull("name")) {
                    document.add(new Paragraph("Organization Name: ", boldFontSmall));
                    document.add(new Paragraph(org.getString("name"), normalFont));
                }
            }
        }

        String okrUrl = "http://localhost:8080/myo-web/objectives/loaddata";
        RestTemplate restTemplate = new RestTemplate();
        String okrDataJson = restTemplate.getForObject(okrUrl, String.class);
        JSONObject okr = new JSONObject(okrDataJson).getJSONObject("data");

        if (okr.has("objectives") && okr.getJSONArray("objectives").length() > 0) {
            PdfPTable okrTable = new PdfPTable(1);
            PdfPCell okrLabelCell = new PdfPCell(new Phrase("OKR Information", boldFont));
            okrLabelCell.setBorder(Rectangle.NO_BORDER);
            okrLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            okrLabelCell.setPaddingBottom(10f);
            okrTable.addCell(okrLabelCell);
            document.add(okrTable);
            document.add(new Chunk(lineSeparator));

            JSONArray objectives = okr.getJSONArray("objectives");
            for (int i = 0; i < objectives.length(); i++) {
                JSONObject objective = objectives.getJSONObject(i);

                PdfPTable objectiveTable = new PdfPTable(1);
                PdfPCell objectiveCell = new PdfPCell(new Phrase("Objective: " + objective.getString("description"), boldFontSmall));
                objectiveCell.setBorder(Rectangle.BOX);
                objectiveCell.setPadding(10f);
                objectiveCell.setPaddingBottom(15f);
                objectiveTable.addCell(objectiveCell);
                document.add(objectiveTable);

                if (objective.has("progress")) {
                    Phrase progressPhrase = new Phrase();
                    progressPhrase.add(new Chunk("Progress: ", boldFontSmall));
                    progressPhrase.add(new Chunk(String.format("%.2f%%", objective.getDouble("progress")), normalFontItalic));
                    if (objective.has("comment")) {
                        Object commentObject = objective.get("comment");
                        if (commentObject instanceof String) {
                            progressPhrase.add(new Chunk(" || " + (String) commentObject, normalFont));
                        } else if (commentObject == JSONObject.NULL) {
                            progressPhrase.add(new Chunk(" || [No comment]", normalFont));
                        } else {
                            progressPhrase.add(new Chunk(" || [Non-string comment]", normalFont));
                        }
                    }
                    document.add(progressPhrase);
                }

                if (objective.has("keyResults") && objective.getJSONArray("keyResults").length() > 0) {
                    JSONArray keyResults = objective.getJSONArray("keyResults");

                    PdfPTable keyResultsTable = new PdfPTable(3);
                    keyResultsTable.setWidthPercentage(100);
                    keyResultsTable.setSpacingBefore(10f);
                    keyResultsTable.setSpacingAfter(10f);

                    PdfPCell keyResultDescHeader = new PdfPCell(new Phrase("Key Result Description", boldFontSmall));
                    keyResultDescHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    keyResultDescHeader.setPadding(8f);
                    keyResultsTable.addCell(keyResultDescHeader);

                    PdfPCell keyResultProgressHeader = new PdfPCell(new Phrase("Progress", boldFontSmall));
                    keyResultProgressHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    keyResultProgressHeader.setPadding(8f);
                    keyResultsTable.addCell(keyResultProgressHeader);

                    PdfPCell keyResultCommentHeader = new PdfPCell(new Phrase("Comment", boldFontSmall));
                    keyResultCommentHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    keyResultCommentHeader.setPadding(8f);
                    keyResultsTable.addCell(keyResultCommentHeader);

                    for (int j = 0; j < keyResults.length(); j++) {
                        JSONObject keyResult = keyResults.getJSONObject(j);

                        PdfPCell descCell = new PdfPCell(new Phrase(keyResult.getString("description"), normalFont));
                        descCell.setBorder(Rectangle.NO_BORDER);
                        descCell.setPadding(5f);
                        keyResultsTable.addCell(descCell);

                        PdfPCell progressCell = new PdfPCell();
                        float progress = (float) keyResult.getDouble("progress");
                        float maxProgress = 100.0f;
                        float progressWidth = 100.0f * progress / maxProgress;

                        PdfPTable progressTable = new PdfPTable(1);
                        progressTable.setWidthPercentage(100);
                        PdfPCell progressCellContainer = new PdfPCell();
                        progressCellContainer.setBorder(Rectangle.NO_BORDER);

                        PdfPTable progressBar = new PdfPTable(1);
                        progressBar.setWidthPercentage(100);
                        PdfPCell progressBarCell = new PdfPCell();
                        progressBarCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        progressBarCell.setFixedHeight(8);
                        progressBar.addCell(progressBarCell);

                        PdfPCell progressIndicatorCell = new PdfPCell();
                        progressIndicatorCell.setBackgroundColor(BaseColor.GRAY);
                        progressIndicatorCell.setFixedHeight(8);
                        progressIndicatorCell.setColspan(1);
                        progressIndicatorCell.setFixedHeight(8);
                        progressIndicatorCell.setPadding(0);
                        progressIndicatorCell.setPaddingLeft(progressWidth);
                        progressBar.addCell(progressIndicatorCell);

                        progressCellContainer.addElement(progressBar);
                        progressTable.addCell(progressCellContainer);
                        progressTable.addCell(new PdfPCell(new Phrase(String.format("%.2f%%", progress), normalFont)));

                        progressCell.addElement(progressTable);
                        keyResultsTable.addCell(progressCell);

                        String keyResultComment = keyResult.has("comment") ? keyResult.optString("comment", "[No comment]") : "[No comment]";
                        PdfPCell commentCell = new PdfPCell(new Phrase(keyResultComment, normalFont));
                        commentCell.setBorder(Rectangle.NO_BORDER);
                        commentCell.setPadding(5f);
                        keyResultsTable.addCell(commentCell);
                    }
                    document.add(keyResultsTable);
                }

                document.add(new Paragraph());
            }
        }

        document.close();
    }
}

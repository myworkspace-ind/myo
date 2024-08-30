package org.sakaiproject.myo.controller;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;

public class ProfilePdfToWordConverter {

    public static void writeProfileToWord(String userDataJson, OutputStream outputStream) throws Exception {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("CV");
        titleRun.setBold(true);
        titleRun.setFontSize(22);
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        JSONObject user = new JSONObject(userDataJson).getJSONObject("data");

        XWPFParagraph basicInfoParagraph = document.createParagraph();
        XWPFRun basicInfoRun = basicInfoParagraph.createRun();
        basicInfoRun.setText("Basic Information");
        basicInfoRun.setBold(true);
        basicInfoRun.setFontSize(18);
        basicInfoParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFParagraph infoParagraph = document.createParagraph();
        XWPFRun infoRun = infoParagraph.createRun();

        if (user.has("email") && !user.isNull("email")) {
            infoRun.setText("Email: " + user.getString("email"));
        }
        if (user.has("username") && !user.isNull("username")) {
            infoRun.setText("  |  Username: " + user.getString("username"));
        }
        if (user.has("userRole") && !user.isNull("userRole")) {
            infoRun.setText("  |  Role: " + user.getString("userRole"));
        }
        if (user.has("telephone") && !user.isNull("telephone")) {
            infoRun.setText("  |  Phone: " + user.getString("telephone"));
        }
        if (user.has("address") && !user.isNull("address")) {
            infoRun.setText("  |  Address: " + user.getString("address"));
        }
        document.createParagraph();

        if (user.has("birthday") && !user.isNull("birthday")) {
            XWPFParagraph birthdayParagraph = document.createParagraph();
            XWPFRun birthdayRun = birthdayParagraph.createRun();
            birthdayRun.setText("Birthday: " + user.getString("birthday"));
            birthdayRun.setBold(true);
            birthdayRun.setFontSize(14);
            document.createParagraph();
        }

        if (user.has("orgs") && user.getJSONArray("orgs").length() > 0) {
            XWPFParagraph orgParagraph = document.createParagraph();
            XWPFRun orgRun = orgParagraph.createRun();
            orgRun.setText("Organization");
            orgRun.setBold(true);
            orgRun.setFontSize(18);
            orgParagraph.setAlignment(ParagraphAlignment.CENTER);

            JSONArray orgs = user.getJSONArray("orgs");
            for (int i = 0; i < orgs.length(); i++) {
                JSONObject org = orgs.getJSONObject(i);
                if (org.has("name") && !org.isNull("name")) {
                    XWPFParagraph orgNameParagraph = document.createParagraph();
                    XWPFRun orgNameRun = orgNameParagraph.createRun();
                    orgNameRun.setText("Organization Name: " + org.getString("name"));
                    orgNameRun.setBold(true);
                    orgNameRun.setFontSize(14);
                    document.createParagraph();
                }
            }
        }

        String okrUrl = "http://localhost:8080/myo-web/objectives/loaddata";
        RestTemplate restTemplate = new RestTemplate();
        String okrDataJson = restTemplate.getForObject(okrUrl, String.class);
        JSONObject okr = new JSONObject(okrDataJson).getJSONObject("data");

        if (okr.has("objectives") && okr.getJSONArray("objectives").length() > 0) {
            XWPFParagraph okrParagraph = document.createParagraph();
            XWPFRun okrRun = okrParagraph.createRun();
            okrRun.setText("OKR Information");
            okrRun.setBold(true);
            okrRun.setFontSize(18);
            okrParagraph.setAlignment(ParagraphAlignment.CENTER);

            JSONArray objectives = okr.getJSONArray("objectives");
            for (int i = 0; i < objectives.length(); i++) {
                JSONObject objective = objectives.getJSONObject(i);

                XWPFParagraph objectiveParagraph = document.createParagraph();
                XWPFRun objectiveRun = objectiveParagraph.createRun();
                objectiveRun.setText("Objective: " + objective.getString("description"));
                objectiveRun.setBold(true);
                objectiveRun.setFontSize(14);

                if (objective.has("progress")) {
                    XWPFParagraph progressParagraph = document.createParagraph();
                    XWPFRun progressRun = progressParagraph.createRun();
                    progressRun.setText("Progress: " + String.format("%.2f%%", objective.getDouble("progress")));
                    progressRun.setItalic(true);
                    progressRun.setFontSize(12);

                    if (objective.has("comment")) {
                        Object commentObject = objective.get("comment");
                        if (commentObject instanceof String) {
                            progressRun.setText(" || " + (String) commentObject);
                        } else if (commentObject == JSONObject.NULL) {
                            progressRun.setText(" || [No comment]");
                        } else {
                            progressRun.setText(" || [Non-string comment]");
                        }
                    }
                }

                if (objective.has("keyResults") && objective.getJSONArray("keyResults").length() > 0) {
                    JSONArray keyResults = objective.getJSONArray("keyResults");

                    XWPFTable keyResultsTable = document.createTable(keyResults.length() + 1, 3);

                    XWPFTableRow headerRow = keyResultsTable.getRow(0);
                    XWPFTableCell headerCell0 = headerRow.getCell(0);
                    XWPFRun headerRun0 = headerCell0.getParagraphs().get(0).createRun();
                    headerRun0.setText("Key Result Description");
                    headerRun0.setBold(true);
                    headerRun0.setFontSize(14);

                    XWPFTableCell headerCell1 = headerRow.getCell(1);
                    XWPFRun headerRun1 = headerCell1.getParagraphs().get(0).createRun();
                    headerRun1.setText("Progress");
                    headerRun1.setBold(true);
                    headerRun1.setFontSize(14);

                    XWPFTableCell headerCell2 = headerRow.getCell(2);
                    XWPFRun headerRun2 = headerCell2.getParagraphs().get(0).createRun();
                    headerRun2.setText("Comment");
                    headerRun2.setBold(true);
                    headerRun2.setFontSize(14);

                    for (int j = 0; j < keyResults.length(); j++) {
                        JSONObject keyResult = keyResults.getJSONObject(j);
                        XWPFTableRow row = keyResultsTable.getRow(j + 1);
                        row.getCell(0).setText(keyResult.getString("description"));
                        row.getCell(1).setText(String.format("%.2f%%", keyResult.getDouble("progress")));
                        row.getCell(2).setText(keyResult.has("comment") ? keyResult.optString("comment", "[No comment]") : "[No comment]");
                    }
                }

                document.createParagraph();
            }
        }

        document.write(outputStream);
    }
}

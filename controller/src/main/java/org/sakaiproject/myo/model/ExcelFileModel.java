package org.sakaiproject.myo.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExcelFileModel {
	private MultipartFile file;
	
	public List<List<String>> getOkrFromExcel(MultipartFile file, int[][] indexes){
		List<List<String>> data = new ArrayList<>();
		try (InputStream is = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(0); // first sheet
			for (int[] index : indexes) {
                int rowIndex = index[0];
                int colIndex = index[1];
                
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        List<String> rowData = new ArrayList<>();
                        switch (cell.getCellType()) {
                            case STRING:
                                rowData.add(cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                rowData.add(String.valueOf(cell.getNumericCellValue()));
                                break;
                            case BOOLEAN:
                                rowData.add(String.valueOf(cell.getBooleanCellValue()));
                                break;
                            default:
                                rowData.add(null); // Handle other types accordingly
                        }
                        data.add(rowData);
                    } else {
                        data.add(new ArrayList<>()); // Empty cell
                    }
                } else {
                    data.add(new ArrayList<>()); // Empty row
                }
                workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}

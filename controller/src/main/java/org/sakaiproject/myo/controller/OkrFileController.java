package org.sakaiproject.myo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.myo.model.ExcelFileModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class OkrFileController {
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView showOkrFile() {
		ModelAndView m = new ModelAndView("okr_file");
		return m;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploadExcel(@ModelAttribute("model") ExcelFileModel model, BindingResult bindingResult) throws IOException{
		ModelAndView m = new ModelAndView("okr_file");
		
		MultipartFile file = model.getFile();
		
		// implement method extract data OKR from excel file in ExcelFileModel and replace "file" with "file.getOkrData()" later
		m.addObject("excel-data", file);
		
		return m;
		
	}
	public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
		List<List<String>> data = new ArrayList<>();

		try (InputStream is = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(is);
			 
			Sheet sheet = workbook.getSheetAt(0); // first sheet
			for (Row row : sheet) {
				List<String> rowData = new ArrayList<>();
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case STRING:
						rowData.add(cell.getStringCellValue());
						break;
					case NUMERIC:
						rowData.add(String.valueOf(cell.getNumericCellValue()));
						break;
					// Handle other cell types as needed
					default:
						rowData.add("");
						break;
					}
				}
				data.add(rowData);
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}

		model.addAttribute("data", data);
		return "okr_file";
	}
}

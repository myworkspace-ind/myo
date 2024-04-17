package org.sakaiproject.myo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.sakaiproject.myo.model.ExcelFileModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
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
	public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @ModelAttribute("model") ExcelFileModel model) throws IOException {
		ModelAndView m = new ModelAndView("okr_file");
		model.setFile(file);
		int[][] indexes = {{2, 12}, {1, 2}, {2, 1}};
		List<List<String>> data = model.getOkrFromExcel(file, indexes);

		m.addObject("data", data);
		return m;
	}
}

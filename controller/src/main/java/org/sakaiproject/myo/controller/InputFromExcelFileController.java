package org.sakaiproject.myo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sakaiproject.myo.model.ExcelFiles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;

@Controller
public class InputFromExcelFileController extends BaseController {
	@RequestMapping(value = { "/upload-excel-file" }, method = RequestMethod.GET)
//	public ModelAndView displayHome(HttpServletRequest request, HttpSession httpSession,Model model) {
//		ModelAndView mav = new ModelAndView("inputFromExcelFile");
//		Objectives obj = new Objectives();
//		model.addAttribute("Objectives", obj);
////		initSession(request, httpSession);
//
//		return mav;
//	}
	public String viewField(Model model) {
		ExcelFiles e = new ExcelFiles();
		model.addAttribute("ExcelFiles", e);
		return "inputFromExcelFile";
	}

	@RequestMapping(value = { "/upload-excel-file" }, method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("excelFile") MultipartFile file, Model model) {
		if (file.isEmpty()) {
			model.addAttribute("message", "Please select an Excel file to upload.");
			System.out.println("Btn is working");
			return "upload-excel-file";
		}

		// Handle the uploaded Excel file (e.g., save it, process it, etc.)

		model.addAttribute("message", "File uploaded successfully: " + file.getOriginalFilename());
		return "upload-excel-file";
	}
}

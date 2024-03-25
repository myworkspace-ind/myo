package org.sakaiproject.myo.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ExcelFiles {
	public ExcelFiles() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExcelFiles(MultipartFile excelFile, List<MultipartFile> excelFiles) {
		super();
		ExcelFile = excelFile;
		ExcelFiles = excelFiles;
	}
	public MultipartFile getExcelFile() {
		return ExcelFile;
	}
	public void setExcelFile(MultipartFile excelFile) {
		ExcelFile = excelFile;
	}
	public List<MultipartFile> getExcelFiles() {
		return ExcelFiles;
	}
	public void setExcelFiles(List<MultipartFile> excelFiles) {
		ExcelFiles = excelFiles;
	}
	
	private MultipartFile ExcelFile;
	private List<MultipartFile> ExcelFiles;
}

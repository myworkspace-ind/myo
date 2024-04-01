package org.sakaiproject.myo.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExcelFileModel {
	private MultipartFile file;
	
}

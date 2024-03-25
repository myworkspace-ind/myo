package org.sakaiproject.myo.model;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Objectives {
	@Override
	public String toString() {
		return "Objectives [objectiveName=" + objectiveName + ", keyResults=" + keyResults + ", contributor="
				+ contributor + ", startingDay=" + startingDay + ", deadline=" + deadline + "]";
	}

	public List<MultipartFile> getImageFiles() {
		return imageFiles;
	}

	public void setImageFiles(List<MultipartFile> imageFiles) {
		this.imageFiles = imageFiles;
	}

	public Objectives() {
		super();
	}
	public Objectives(String objectiveName, String keyResults, String contributor, Date startingDay, Date deadline,
			MultipartFile imageFile, List<MultipartFile> imageFiles) {
		super();
		this.objectiveName = objectiveName;
		this.keyResults = keyResults;
		this.contributor = contributor;
		this.startingDay = startingDay;
		this.deadline = deadline;
		this.imageFile = imageFile;
		this.imageFiles = imageFiles;
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		this.objectiveName = objectiveName;
	}

	public String getKeyResults() {
		return keyResults;
	}

	public void setKeyResults(String keyResults) {
		this.keyResults = keyResults;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public Date getStartingDay() {
		return startingDay;
	}

	public void setStartingDay(Date startingDay) {
		this.startingDay = startingDay;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}




	private String objectiveName;
	private String keyResults;
	private String contributor;
	private Date startingDay;
	private Date deadline;
	private MultipartFile imageFile;
	private List<MultipartFile> imageFiles;

}

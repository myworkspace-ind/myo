package org.sakaiproject.myo;

import org.springframework.http.ResponseEntity;

public interface IOkrBackend {
	String getPeriod();
	String getCurrentPeriodId();
	String getOrganization();
	String getObjectives();
	public ResponseEntity<String> postOkr(String jsonData);
	public ResponseEntity<String> postPeriod(String jsonData);
	public boolean deleteObjectives(String Id);
}

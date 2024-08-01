package org.sakaiproject.myo;

import org.springframework.http.ResponseEntity;

public interface IOkrBackend {
	String getPeriod();
	String getCurrentPeriodId();
	String getOrganization();
	String getObjectives(String periodId, String organizationId);
	public ResponseEntity<String> postOkr(String jsonData);
}

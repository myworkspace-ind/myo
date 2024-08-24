package org.sakaiproject.myo;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface IOkrBackend {
	String getPeriod();
	String getCurrentPeriodId();
	String getOrganization();
//	String getObjectives(String periodId, String organizationId);
	String getObjectives();
	public ResponseEntity<String> postOkr(String jsonData);
	public ResponseEntity<String> postPeriod(String jsonData);
	public boolean deleteObjectives(String Id);
	
	public ResponseEntity<String> updateKeyResultGrade(String keyResultId, String payload);
	public ResponseEntity<String> updateOkrDraftSave(String updatedOkr);
}

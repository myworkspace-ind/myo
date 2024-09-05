package org.sakaiproject.myo;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface IOkrBackend {
	String getPeriod();
	String getCurrentPeriodId();
	String getOrganization();
	String getAllOrganization();
	String getUserInOrganization(String id);
	String getProfile();	
	String getAllUsers();
	String getObjectives(String periodId, String organizationId);
	String getUserRole();
	public ResponseEntity<String> postOkr(String jsonData);
	public ResponseEntity<String> createOrganization(String jsonData);
	public ResponseEntity<String> postProfile(String jsonData);
	public ResponseEntity<String> postPeriod(String jsonData);
	public ResponseEntity<String> addMember(String jsonData);
	public ResponseEntity<String> updateOrganization(String orgId, String jsonData);
	public boolean deleteObjectives(String Id);
	public boolean deleteOrganization(String Id);
	
	public ResponseEntity<String> updateKeyResultGrade(String keyResultId, String payload);
	public ResponseEntity<String> updateOkrDraftSave(String updatedOkr);
}

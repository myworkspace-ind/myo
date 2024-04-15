package org.sakaiproject.myo.service;

import org.sakaiproject.myo.repository.OkrRepository;

public interface OkrService {
	OkrRepository getRepo();
	
	void addOKR(String name, String status, String complete, String notes);
}

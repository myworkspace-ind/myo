package org.sakaiproject.myo.service;

import org.sakaiproject.myo.repository.OkrRepository;

public interface OkrService {
	OkrRepository getRepo();
	void addOkr();
	
}

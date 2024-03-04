package org.sakaiproject.myo.service.impl;

import org.sakaiproject.myo.repository.OkrRepository;
import org.sakaiproject.myo.service.OkrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkrServiceImpl implements OkrService {

	@Autowired
	OkrRepository okrRepo;

	@Override
	public OkrRepository getRepo() {
		return okrRepo;
	}
}

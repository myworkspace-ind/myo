package org.sakaiproject.myo.service.impl;

import org.sakaiproject.myo.repository.OkrRepository;
import org.sakaiproject.myo.service.OkrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkrServiceImpl implements OkrService {
	
	private final OkrRepository okrRepo;
	
	@Autowired
	public OkrServiceImpl(OkrRepository okrRepo) {
		this.okrRepo = okrRepo;
	};

	@Override
	public OkrRepository getRepo() {
		return okrRepo;
	}
	
	@Override
	public void addOKR(String name, String status, String complete, String notes) {
		okrRepo.insertnewOkr(name, status, complete, notes);	
	
	}
	

	
}

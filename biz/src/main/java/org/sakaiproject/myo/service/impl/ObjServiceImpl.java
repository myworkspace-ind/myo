package org.sakaiproject.myo.service.impl;

import org.sakaiproject.myo.repository.ObjRepository;
import org.sakaiproject.myo.service.ObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjServiceImpl implements ObjService {
	@Autowired
	ObjRepository objRepo;

	@Override
	public ObjRepository getRepo() {
		return objRepo;
	}

//	@Override
//	public List<OkrObj> findAll() {
//		return objRepo.findAll();
//	}
}

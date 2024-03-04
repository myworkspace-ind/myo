package org.sakaiproject.myo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.myo.entity.OkrOrg;
import org.sakaiproject.myo.repository.OrgRepository;
import org.sakaiproject.myo.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl implements OrgService {
	@Autowired
	OrgRepository okrRepo;
	
	public List<OkrOrg> findAll() {
		List<OkrOrg> lstOrg = okrRepo.findAll(); 
		return (lstOrg != null) ? lstOrg : new ArrayList<OkrOrg>(0);
	}
}

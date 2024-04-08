package org.sakaiproject.myo.service;

import java.util.List;

import org.sakaiproject.myo.entity.OkrPeriod;
import org.sakaiproject.myo.repository.UserRepository;

public interface PeriService {
	public List<OkrPeriod> findAll();
	UserRepository getRepo();
}

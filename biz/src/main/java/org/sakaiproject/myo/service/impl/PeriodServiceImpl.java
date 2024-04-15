package org.sakaiproject.myo.service.impl;

import java.util.List;

import org.sakaiproject.myo.entity.OkrPeriod;
import org.sakaiproject.myo.repository.PeriodRepository;
import org.sakaiproject.myo.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodServiceImpl implements PeriodService {

	@Autowired
	private PeriodRepository PeriodRepo;

	@Override
	public List<OkrPeriod> findAll() {
		return PeriodRepo.findAll();
	}

	@Override
	public PeriodRepository getPeriodRepository() {
		return PeriodRepo;
	}

}


package org.sakaiproject.myo.service;

import java.util.List;

import org.sakaiproject.myo.entity.OkrPeriod;
import org.sakaiproject.myo.repository.PeriodRepository;

public interface PeriodService {
	public List<OkrPeriod> findAll();
	PeriodRepository getPeriodRepository();
}

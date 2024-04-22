package org.sakaiproject.myo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
	
	public void processPeriods() {
		// Retrieve all periods from the database
        List<OkrPeriod> periods = PeriodRepo.findAll();

        // Process the retrieved periods
        for (OkrPeriod period : periods) {
            // Access individual attributes of each OkrPeriod object
            UUID id = period.getId();
            Date startDate = period.getStartDate();
            Date endDate = period.getEndDate();
            String name = period.getName();
            String note = period.getNote();
            // etc. - access other attributes similarly

            // Perform any necessary operations with the retrieved data
            // For example, you can print it
            System.out.println("Period: " + id + ", " + startDate + ", " + endDate + ", " + name + ", " + note);
        }
	}

}


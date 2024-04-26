/**
 * Licensed to MKS Group under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * MKS Group licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.sakaiproject.myo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sakaiproject.myo.entity.OkrPeriod;
import org.sakaiproject.myo.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class PeriodController extends BaseController {
	@Autowired
	PeriodService periodService;

	/**
	 * Simply selects the home view to render by returning its name.
     * @return 
	 */
	@RequestMapping(value = {"/period"}, method = RequestMethod.GET)
	public ModelAndView displayPeriod(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("period");
		initSession(request, httpSession);
		List<List<Object>> data = new ArrayList<>();

		List<OkrPeriod> allPeriods = periodService.findAll(); 

		List<Object> periodYear = new ArrayList<>();
		List<Object> periodName = new ArrayList<>();
		List<Object> periodStartDate = new ArrayList<>();
		List<Object> periodEndDate = new ArrayList<>();
		List<Object> periodNote = new ArrayList<>();

		for (OkrPeriod period : allPeriods) {
			List<Object> periodData = new ArrayList<>();
			periodData.add(String.valueOf(period.getYear()));
			periodYear.add(String.valueOf(period.getYear()));
			periodData.add(String.valueOf(period.getName()));
			periodName.add(String.valueOf(period.getName()));
			periodData.add(String.valueOf(period.getStartDate()));
			periodStartDate.add(String.valueOf(period.getStartDate())); 
			periodData.add(String.valueOf(period.getEndDate()));
			periodEndDate.add(String.valueOf(period.getEndDate()));
			periodData.add(String.valueOf(period.getNote()));
			periodNote.add(String.valueOf(period.getNote()));
            // Add other properties as needed
            data.add(periodData);
        }

		
		System.out.println("Periods found with findAll():");
		System.out.println("-------------------------------");
		
		int len = (allPeriods != null) ? allPeriods.size(): 0;
		System.out.println("Number of periods: " + len);
		
		int lenTest = (data != null) ? data.size(): 0;
		System.out.println("Number of periods data: " + lenTest);
		System.out.println("Data: " + data);
		
		periodService.processPeriods();

		mav.addObject("periods", allPeriods);
		mav.addObject("period", data);
		mav.addObject("periodYear", periodYear);
		mav.addObject("periodName", periodName);
		mav.addObject("periodStartDate", periodStartDate);
		mav.addObject("periodEndDate", periodEndDate);
		mav.addObject("periodNote", periodNote);

		return mav;
	}

}

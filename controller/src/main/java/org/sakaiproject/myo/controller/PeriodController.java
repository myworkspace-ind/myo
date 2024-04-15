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

		List<OkrPeriod> allPeriods = periodService.findAll(); 
		
		int lenPeri = (allPeriods != null) ? allPeriods.size(): 0;
		

		log.info("Number of users: " + lenPeri);

		mav.addObject("periods", allPeriods);

		return mav;
	}

}

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

import org.sakaiproject.myo.IOkrBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
//@Slf4j
public class CrudController extends BaseController {
 
	@Autowired
	IOkrBackend serviceOkrBackend;
	
    @GetMapping(value = "crud")
	public ModelAndView displayLandingPage(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("crud");
		return mav;
	}
    
    @GetMapping(value = "/period/loaddata")
	@ResponseBody
    public String getInfoPeriod() {
    	// Get and process response
    	//System.out.print(serviceOkrBackend.getPeriod());
    	return serviceOkrBackend.getPeriod();
    }    
    
    @GetMapping(value = "/organization/loaddata")
	@ResponseBody
    public String getInfoOrganization() {
    	// Get and process response
    	//System.out.print(serviceOkrBackend.getOrganization());
    	return serviceOkrBackend.getOrganization();
    } 
    
    @GetMapping(value = "/objectives/loaddata")
	@ResponseBody
    public String getInfoObjectives() {
    	// Get and process response
    	//System.out.print(serviceOkrBackend.getObjectives());
    	return serviceOkrBackend.getObjectives();
    } 
//	@RequestMapping(value = {"/crud/{objName}"}, method = RequestMethod.GET)
//	public ModelAndView crud(@PathVariable("objName") String objName, HttpServletRequest request, HttpSession httpSession) {
//		log.debug("Start CRUD object " + objName);
//
//		ModelAndView mav = new ModelAndView("crud");
//
//		initSession(request, httpSession);
//
//		mav.addObject("currentSiteId", getCurrentSiteId());
//		mav.addObject("userDisplayName", getCurrentUserDisplayName());
//
//		List<OkrUser> allUsers = userService.findAll();
//		int len = (allUsers != null) ? allUsers.size(): 0;
//		log.info("Number of users: " + len);
//
//		mav.addObject("users", allUsers);
//		
//		mav.addObject("orgs", orgService.findAll());
//
//		return mav;
//	}
}

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

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.service.OrgService;
import org.sakaiproject.myo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class HomeController extends BaseController {
 
	@Autowired
	UserService userService;
	
	@Autowired
	OrgService orgService;

	/**
	 * Simply selects the home view to render by returning its name.
     * @return 
	 */
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public ModelAndView displayHome(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("home");
		initSession(request, httpSession);

//		mav.addObject("currentSiteId", getCurrentSiteId());
//		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		List<OkrUser> allUsers = userService.findAll();
		int len = (allUsers != null) ? allUsers.size(): 0;
		System.out.println("Number of users: " + len);
		System.out.println("Hello World!");
		mav.addObject("users", allUsers);
		
		mav.addObject("orgs", orgService.findAll());

		return mav;
	}
    

}

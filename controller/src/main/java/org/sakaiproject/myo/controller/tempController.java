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

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.repository.UserRepositoryProfile;
import org.sakaiproject.myo.service.OrgService;
import org.sakaiproject.myo.service.UserService;
import org.sakaiproject.myo.service.UserServiceProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Slf4j
@Controller
public class tempController extends BaseController {

	/**
	 * This method is called when binding the HTTP parameter to bean (or model).
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// Sample init of Custom Editor

//        Class<List<ItemKine>> collectionType = (Class<List<ItemKine>>)(Class<?>)List.class;
//        PropertyEditor orderNoteEditor = new MotionRuleEditor(collectionType);
//        binder.registerCustomEditor((Class<List<ItemKine>>)(Class<?>)List.class, orderNoteEditor);

	}

	@Autowired
	UserServiceProfile userService;
	@Autowired
	UserRepositoryProfile userRepositoryProfile;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "temp" }, method = RequestMethod.GET)
	public ModelAndView displayHome(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("temp");
		/*
		 * List<OkrUserProfile> allUsers = userService.findAll(); int len = (allUsers !=
		 * null) ? allUsers.size(): 0; log.info("Number of users: " + len);
		 * mav.addObject("users", allUsers);
		 */

		String userEmail = "micrayon2812@gmail.com";
		OkrUserProfile user = userRepositoryProfile.findByEmail(userEmail);

		if (user != null) {
			mav.addObject("user", user);

		} 
		return mav;
	}

}

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.entity.OkrUserProfileDetail;
import org.sakaiproject.myo.repository.UserRepositoryProfile;
import org.sakaiproject.myo.repository.UserRepositoryProfileDetail;
import org.sakaiproject.myo.service.UserServiceProfile;
import org.sakaiproject.myo.service.UserServiceProfileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MyPrivacyController extends BaseController {
 
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
	UserServiceProfileDetail userServiceDetail;
	@Autowired
	UserRepositoryProfileDetail userRepositoryProfileDetail;
    
	/**
	 * Simply selects the home view to render by returning its name.
     * @return 
	 */
	@RequestMapping(value = {"MyPrivacy"}, method = RequestMethod.GET)
	public ModelAndView displayHome(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("my_privacy");

		/*
		 * initSession(request, httpSession);
		 */		
		String uuid = "179f6307-fa79-4639-9b5f-2bab7ab6f370";
		OkrUserProfileDetail userdetail = userRepositoryProfileDetail.findByUUID(uuid);
		
		if (userdetail != null) {
			mav.addObject("user", userdetail);
            System.out.println(userdetail.getProfileImagePrivacy());

		} 

		return mav;
	}
}

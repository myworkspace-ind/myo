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
import org.sakaiproject.myo.backend.OkrBackend;
import org.sakaiproject.myo.service.PeriodService;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;


import lombok.extern.slf4j.Slf4j;
import java.util.Map;



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
	public String homePage(Model model, HttpSession session) {
        // Check if the user is logged in (this assumes a session attribute 'loggedIn' is being set)
        String authToken = (String) session.getAttribute("authToken");

        if (authToken == null || authToken.isEmpty()) {
            // If the user is not logged in, redirect to the login page
            return "redirect:/auth";
        }
        else {

        // If logged in, proceed to the homepage
        model.addAttribute("loggedIn", authToken);
        return "home"; // The name of your homepage Thymeleaf template
        }
    }

	
    

}

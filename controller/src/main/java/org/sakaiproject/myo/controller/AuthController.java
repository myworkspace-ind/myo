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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sakaiproject.myo.backend.OkrBackend;
import org.sakaiproject.myo.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class AuthController extends BaseController {
	
	@Autowired
	PeriodService periodService;

	@Autowired
	OkrBackend okrBackend;
	
	@GetMapping("/auth")
    public String showLoginPage(HttpSession session, Model model) {
        if (session.getAttribute("authToken") != null) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
        return "auth";
    }
	
	@PostMapping("/auth/login")
    public String login(HttpServletRequest request, HttpSession session, Model model) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

		try {
	        String token = okrBackend.getAuthToken(username, password);
	        if (token != null) {
	            // Save token or set it in the session or context
	        	session.setAttribute("authToken", token);
	            okrBackend.setOkrAuthToken(token);
	            return "redirect:/";
	        } else {
	            model.addAttribute("error", "Invalid username or password");
	            return "auth";
	        }
	    } catch (Exception e) {
	    	// System.out.println("Error message: " + e.getMessage());
	        model.addAttribute("error", e.getMessage());
	        return "auth";
	    }
    }

    @PostMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("authToken");
        okrBackend.setOkrAuthToken(null);
        return "redirect:/auth";
    }

}

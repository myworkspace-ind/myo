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

import java.io.Console;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sakaiproject.myo.IOkrBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		ModelAndView mav = new ModelAndView("crud_new");

		return mav;
	}

//	@PostMapping(value = "/objectives/uploaddata")
//	@ResponseBody
//	public String postInfoObjectives(@RequestBody String data) {
//		System.out.print(data);
//
//		return data;
//
//	}

//	@PostMapping(value = "/period/uploaddata")
//	@ResponseBody
//	public String postInfoPeriod(@RequestBody String data) {
//		System.out.print(data);
//
//		return data;
//
//	}

	@GetMapping(value = "/period/loaddata")
	@ResponseBody
	public String getInfoPeriod() {
		// Get and process response
		// System.out.print(serviceOkrBackend.getPeriod());
		return serviceOkrBackend.getPeriod();
	}

	@GetMapping(value = "/organization/loaddata")
	@ResponseBody
	public String getInfoOrganization() {
		// Get and process response
		// System.out.print(serviceOkrBackend.getOrganization());
		return serviceOkrBackend.getOrganization();
	}

	@GetMapping(value = "/objectives/loaddata")
	@ResponseBody
	public String getInfoObjectives() {
		// Get and process response
		// System.out.print(serviceOkrBackend.getObjectives());
		return serviceOkrBackend.getObjectives();
	}
    
    @PostMapping("/objectives/uploaddata")
    public ResponseEntity<String> createOkr(@RequestBody String jsonData) {
    	System.out.println("post--0");
    	JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
    	System.out.println("post0");
        // Add default values for attributes that may be missing
        if (!jsonObject.has("status")) {
            jsonObject.addProperty("status", "DRAFT");
        }
        if (!jsonObject.has("progress")) {
            jsonObject.addProperty("progress", 0.0);
        }
        if (!jsonObject.has("grade")) {
            jsonObject.addProperty("grade", 0.0);
        }
        if (!jsonObject.has("organizationId")) {
            jsonObject.addProperty("organizationId", serviceOkrBackend.getOrganization());
        }
        if (!jsonObject.has("periodId")) {
            jsonObject.addProperty("periodId", serviceOkrBackend.getCurrentPeriodId());
        }

        // Convert modified JSON object back to string
        String modifiedJsonData = jsonObject.toString();
        
        System.out.println("post");
        System.out.println(jsonObject);
        System.out.println(modifiedJsonData);

        // Pass modifiedJsonData to serviceOkrBackend for further processing
        return serviceOkrBackend.postOkr(modifiedJsonData);
    }
    
    @PostMapping("/period/uploaddata")
    public ResponseEntity<String> createPeriod(@RequestBody String jsonData) {
        return serviceOkrBackend.postPeriod(jsonData);
    }
}

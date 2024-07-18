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

import org.sakaiproject.myo.model.OkrListModel;
import org.sakaiproject.myo.model.TableStructure;
import org.sakaiproject.myo.service.OrgService;
import org.sakaiproject.myo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;





import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class NewOKRController extends BaseController {

	@Value("${okr.colHeaders}")
	private String[] okrColHeaders;

	private int[] okrColWidths = {200, 300, 150, 150, 400};

	@Autowired
	UserService userService;

	@Autowired
	OrgService orgService;
/**
	 * This method is called when binding the HTTP parameter to bean (or model).
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// Sample init of Custom Editor

		/*
		 * Class<List<ItemKine>> collectionType =
		 * (Class<List<ItemKine>>)(Class<?>)List.class; PropertyEditor orderNoteEditor =
		 * new MotionRuleEditor(collectionType);
		 * binder.registerCustomEditor((Class<List<ItemKine>>)(Class<?>)List.class,
		 * orderNoteEditor);
		 */

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @return
	 */	@GetMapping(value = "newokr")
	public ModelAndView displayLandingPage(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("newokr");
		return mav;
	}

	@GetMapping(value = { "/newokr/load/okr" }, produces = "application/json")
	@ResponseBody
	public TableStructure getEmployeeTableData() {

		List<Object[]> lstOkrs = null;
		TableStructure empTable = new TableStructure(okrColWidths, okrColHeaders, lstOkrs);

		return empTable;
	}

	@PostMapping(value = "/newokr/save")
	@ResponseBody
	public String processSave(@ModelAttribute("model") OkrListModel model, BindingResult bindingResult,
			MultipartHttpServletRequest request) {

		// Debug
		/*
		 * log.debug("Submitted data:" + model.getData());
		 */
		System.out.println(model.getData());

		return "OK";
	}
}

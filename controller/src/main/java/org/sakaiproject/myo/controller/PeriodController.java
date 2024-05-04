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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;
import org.sakaiproject.myo.model.TableStructure;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class PeriodController extends BaseController {
	@Autowired
	PeriodService periodService;

	@Value("${productList.colHeaders:Year,Name,Start date,End date,Note}")
	private String[] productListColHeaders;

	@Value("${productList.colWidths:80,250,200,200,80}")
	private int[] productListColWidths;

	@GetMapping(value = "/period")
	public ModelAndView displayPeriod(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("period");
		System.out.println("productListColWidths");

		return mav;
	}



	@GetMapping(value = { "/period/loaddata" }, produces = "application/json")
	@ResponseBody
	public TableStructure getProductTableData() {
		List<Object[]> lstProducts = getDemoData();

		TableStructure productTable = new TableStructure(productListColWidths, productListColHeaders, lstProducts);

		return productTable;
	}

	private List<Object[]> getDemoData() {
		List<Object[]> data = new ArrayList<>();
		List<OkrPeriod> periods = periodService.findAll();

		for (OkrPeriod period : periods) {
			Object[] row = new Object[5];

			row[0] = period.getYear();
			row[1] = period.getName();
			row[2] = period.getStartDate();
			row[3] = period.getEndDate();
			row[4] = period.getNote();

			data.add(row);
		}

		return data;
	}

}

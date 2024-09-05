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
import javax.servlet.http.HttpSession;

import org.sakaiproject.myo.IOkrBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrganizationController extends BaseController {
	@Autowired
	IOkrBackend serviceOkrBackend;

	@GetMapping(value = "/organization")
	public ModelAndView displayPeriod(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("organization");
		return mav;
	}

	@GetMapping(value = "/organization/loadalldata")
	@ResponseBody
	public String getAllOrganization() {
		// System.out.print(serviceOkrBackend.getOrganization());
		return serviceOkrBackend.getAllOrganization();
	}

	@GetMapping(value = "/userRole/load")
	@ResponseBody
	public String getUserRole() {
		System.out.print(serviceOkrBackend.getUserRole());
		return serviceOkrBackend.getUserRole();
	}

	@GetMapping(value = "/organization/users/{orgId}")
	@ResponseBody
	public String getUserInOrganization(@PathVariable String orgId) {
		// System.out.print(serviceOkrBackend.getOrganization());
		return serviceOkrBackend.getUserInOrganization(orgId);
	}

	@GetMapping(value = "/userprofile/{userId}")
	@ResponseBody
	public String getSelectedUser(@PathVariable String userId) {
		// System.out.print(serviceOkrBackend.getOrganization());
		return serviceOkrBackend.getSelectedUser(userId);
	}
	
	@GetMapping(value = "/userprofile/admin/getListUserProfile")
	@ResponseBody
	public String getAllUsers() {
		// System.out.print(serviceOkrBackend.getOrganization());
		return serviceOkrBackend.getAllUsers();
	}

	@PostMapping("/organization/create")
	public ResponseEntity<String> createOrganization(@RequestBody String jsonData) {
		return serviceOkrBackend.createOrganization(jsonData);
	}

	@PostMapping("/organization/addMember")
	public ResponseEntity<String> addMember(@RequestBody String jsonData) {
		return serviceOkrBackend.addMember(jsonData);
	}

	@DeleteMapping("/organization/delete/{selectedOrgId}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable String selectedOrgId) {
		try {
			boolean isDeleted = serviceOkrBackend.deleteOrganization(selectedOrgId);
			if (isDeleted) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete failed");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/organization/update/{orgId}")
	public ResponseEntity<String> updateOrganization(@PathVariable String orgId, @RequestBody String jsonData) {
		return serviceOkrBackend.updateOrganization(orgId, jsonData);
	}

}

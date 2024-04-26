
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
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Slf4j
@Controller
public class UpdateProfileController extends BaseController {

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
	@RequestMapping(value = { "MyProfileUpdate" }, method = RequestMethod.GET)
	public ModelAndView updateProfile(@RequestParam(required = false) String description,
	        @RequestParam(required = false) String Name, 
	        @RequestParam(required = false) String Address,
	        @RequestParam(required = false) String Pronouns, 
	        @RequestParam(required = false) String PhoneticPronunciation,
	        @RequestParam(required = false) String CommonName, 
	        @RequestParam(required = false) String Email,
	        @RequestParam(required = false) String Homepage, 
	        @RequestParam(required = false) String Workphone,
	        @RequestParam(required = false) String Homephone, 
	        @RequestParam(required = false) String Mobilephone,
	        @RequestParam(required = false) String Facsimile, 
	        @RequestParam(required = false) String Position,
	        @RequestParam(required = false) String Department, 
	        @RequestParam(required = false) String School,
	        @RequestParam(required = false) String Room, 
	        @RequestParam(required = false) String Staffprofile,
	        @RequestParam(required = false) String UniversityprofileURL,
	        @RequestParam(required = false) String AcademicresearchURL,
	        @RequestParam(required = false) String Publicationsandconferences,
	        @RequestParam(required = false) String Course,
	        @RequestParam(required = false) String Subjects,
	        @RequestParam(required = false) String books,
	        @RequestParam(required = false) String shows,
	        @RequestParam(required = false) String movies,
	        @RequestParam(required = false) String quotes) {
	    String email = "micrayon2812@gmail.com";

	    description = (description != null && !description.isEmpty()) ? description : null;
	    Name = (Name != null && !Name.isEmpty()) ? Name : null;
	    Address = (Address != null && !Address.isEmpty()) ? Address : null;
	    Pronouns = (Pronouns != null && !Pronouns.isEmpty()) ? Pronouns : null;
	    PhoneticPronunciation = (PhoneticPronunciation != null && !PhoneticPronunciation.isEmpty()) ? PhoneticPronunciation : null;
	    CommonName = (CommonName != null && !CommonName.isEmpty()) ? CommonName : null;
	    Email = (Email != null && !Email.isEmpty()) ? Email : null;
	    Homepage = (Homepage != null && !Homepage.isEmpty()) ? Homepage : null;
	    Workphone = (Workphone != null && !Workphone.isEmpty()) ? Workphone : null;
	    Homephone = (Homephone != null && !Homephone.isEmpty()) ? Homephone : null;
	    Mobilephone = (Mobilephone != null && !Mobilephone.isEmpty()) ? Mobilephone : null;
	    Facsimile = (Facsimile != null && !Facsimile.isEmpty()) ? Facsimile : null;
	    Position = (Position != null && !Position.isEmpty()) ? Position : null;
	    Department = (Department != null && !Department.isEmpty()) ? Department : null;
	    School = (School != null && !School.isEmpty()) ? School : null;
	    Room = (Room != null && !Room.isEmpty()) ? Room : null;
	    Staffprofile = (Staffprofile != null && !Staffprofile.isEmpty()) ? Staffprofile : null;
	    UniversityprofileURL = (UniversityprofileURL != null && !UniversityprofileURL.isEmpty()) ? UniversityprofileURL : null;
	    AcademicresearchURL = (AcademicresearchURL != null && !AcademicresearchURL.isEmpty()) ? AcademicresearchURL : null;
	    Publicationsandconferences = (Publicationsandconferences != null && !Publicationsandconferences.isEmpty()) ? Publicationsandconferences : null;
	    Course = (Course != null && !Course.isEmpty()) ? Course : null;
	    Subjects = (Subjects != null && !Subjects.isEmpty()) ? Subjects : null;
	    books = (books != null && !books.isEmpty()) ? books : null;
	    shows = (shows != null && !shows.isEmpty()) ? shows : null;
	    movies = (movies != null && !movies.isEmpty()) ? movies : null;
	    quotes = (quotes != null && !quotes.isEmpty()) ? quotes : null;

	    userService.updateUserProfile(email, description, Name, Address, Pronouns, PhoneticPronunciation, CommonName,
	            Email, Homepage, Workphone, Homephone, Mobilephone, Facsimile, Position, Department, School, Room,
	            Staffprofile, UniversityprofileURL, AcademicresearchURL, Publicationsandconferences, Course,
	            Subjects, books, shows, movies, quotes);

	    ModelAndView mav = new ModelAndView("redirect:/MyProfile");

	    return mav;
	}


}

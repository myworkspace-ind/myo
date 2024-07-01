
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
import org.sakaiproject.myo.repository.UserRepositoryProfileDetail;
import org.sakaiproject.myo.service.OrgService;
import org.sakaiproject.myo.service.UserService;
import org.sakaiproject.myo.service.UserServiceProfile;
import org.sakaiproject.myo.service.UserServiceProfileDetail;
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
public class UpdatePreferencesController extends BaseController {

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
	UserServiceProfileDetail userService;
	@Autowired
	UserRepositoryProfileDetail userRepositoryProfile;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "MyPreferencesUpdate" }, method = RequestMethod.GET)
	public ModelAndView updatePreferences(@RequestParam(required = false) String profileImagePrivacy,
			@RequestParam(required = false) String basicInfoPrivacy,
			@RequestParam(required = false) String contactInfoPrivacy,
			@RequestParam(required = false) String staffInfoPrivacy,
			@RequestParam(required = false) String studentInfoPrivacy,
			@RequestParam(required = false) String socialInfoPrivacy,
			@RequestParam(required = false) String personalInfoPrivacy,
			@RequestParam(required = false) Boolean showBirthYear,
			@RequestParam(required = false) String viewConnectionsPrivacy,
			@RequestParam(required = false) String viewOnlineStatusPrivacy,
			@RequestParam(required = false) String viewStatusUpdatesPrivacy,
			@RequestParam(required = false) String viewPicturesPrivacy,
			@RequestParam(required = false) String sendMessagesPrivacy,
			@RequestParam(required = false) String viewKudosRatingPrivacy,
			@RequestParam(required = false) String emailNotificationsAdds,
			@RequestParam(required = false) String emailNotificationsConnectionRequest,
			@RequestParam(required = false) String emailNotificationsNewMessage,
			@RequestParam(required = false) String emailNotificationsReplyMessage,
			@RequestParam(required = false) String emailNotificationsAddWorksite,
			@RequestParam(required = false) Boolean twitterIntegrationStatusUpdate,
			@RequestParam(required = false) Boolean useGravatar, @RequestParam(required = false) Boolean showKudosRating,
			@RequestParam(required = false) Boolean showPictures,
			@RequestParam(required = false) Boolean showOnlineStatus) {
		String uuid = "179f6307-fa79-4639-9b5f-2bab7ab6f370";

	    uuid = (uuid != null && !uuid.isEmpty()) ? uuid : null;
	    profileImagePrivacy = (profileImagePrivacy != null && !profileImagePrivacy.isEmpty()) ? profileImagePrivacy : null;
	    basicInfoPrivacy = (basicInfoPrivacy != null && !basicInfoPrivacy.isEmpty()) ? basicInfoPrivacy : null;
	    contactInfoPrivacy = (contactInfoPrivacy != null && !contactInfoPrivacy.isEmpty()) ? contactInfoPrivacy : null;
	    staffInfoPrivacy = (staffInfoPrivacy != null && !staffInfoPrivacy.isEmpty()) ? staffInfoPrivacy : null;
	    studentInfoPrivacy = (studentInfoPrivacy != null && !studentInfoPrivacy.isEmpty()) ? studentInfoPrivacy : null;
	    socialInfoPrivacy = (socialInfoPrivacy != null && !socialInfoPrivacy.isEmpty()) ? socialInfoPrivacy : null;
	    personalInfoPrivacy = (personalInfoPrivacy != null && !personalInfoPrivacy.isEmpty()) ? personalInfoPrivacy : null;
	    viewConnectionsPrivacy = (viewConnectionsPrivacy != null && !viewConnectionsPrivacy.isEmpty()) ? viewConnectionsPrivacy : null;
	    viewOnlineStatusPrivacy = (viewOnlineStatusPrivacy != null && !viewOnlineStatusPrivacy.isEmpty()) ? viewOnlineStatusPrivacy : null;
	    viewStatusUpdatesPrivacy = (viewStatusUpdatesPrivacy != null && !viewStatusUpdatesPrivacy.isEmpty()) ? viewStatusUpdatesPrivacy : null;
	    viewPicturesPrivacy = (viewPicturesPrivacy != null && !viewPicturesPrivacy.isEmpty()) ? viewPicturesPrivacy : null;
	    sendMessagesPrivacy = (sendMessagesPrivacy != null && !sendMessagesPrivacy.isEmpty()) ? sendMessagesPrivacy : null;
	    viewKudosRatingPrivacy = (viewKudosRatingPrivacy != null && !viewKudosRatingPrivacy.isEmpty()) ? viewKudosRatingPrivacy : null;
	    emailNotificationsAdds = (emailNotificationsAdds != null && !emailNotificationsAdds.isEmpty()) ? emailNotificationsAdds : null;
	    emailNotificationsConnectionRequest = (emailNotificationsConnectionRequest != null && !emailNotificationsConnectionRequest.isEmpty()) ? emailNotificationsConnectionRequest : null;
	    emailNotificationsNewMessage = (emailNotificationsNewMessage != null && !emailNotificationsNewMessage.isEmpty()) ? emailNotificationsNewMessage : null;
	    emailNotificationsReplyMessage = (emailNotificationsReplyMessage != null && !emailNotificationsReplyMessage.isEmpty()) ? emailNotificationsReplyMessage : null;
	    emailNotificationsAddWorksite = (emailNotificationsAddWorksite != null && !emailNotificationsAddWorksite.isEmpty()) ? emailNotificationsAddWorksite : null;
	    twitterIntegrationStatusUpdate = (twitterIntegrationStatusUpdate != null) ? twitterIntegrationStatusUpdate : null;
	    useGravatar = (useGravatar != null) ? useGravatar : null;
	    showKudosRating = (showKudosRating != null) ? showKudosRating : null;
	    showPictures = (showPictures != null) ? showPictures : null;
	    showOnlineStatus = (showOnlineStatus != null) ? showOnlineStatus : null;

		userService.updateUserProfileDetail(uuid, profileImagePrivacy, basicInfoPrivacy, contactInfoPrivacy,
				staffInfoPrivacy, studentInfoPrivacy, socialInfoPrivacy, personalInfoPrivacy, showBirthYear,
				viewConnectionsPrivacy, viewOnlineStatusPrivacy, viewStatusUpdatesPrivacy, viewPicturesPrivacy,
				sendMessagesPrivacy, viewKudosRatingPrivacy, emailNotificationsAdds,
				emailNotificationsConnectionRequest, emailNotificationsNewMessage, emailNotificationsReplyMessage,
				emailNotificationsAddWorksite, twitterIntegrationStatusUpdate, useGravatar, showKudosRating,
				showPictures, showOnlineStatus);

		ModelAndView mav = new ModelAndView("redirect:/MyPreferences");

		return mav;
	}

}

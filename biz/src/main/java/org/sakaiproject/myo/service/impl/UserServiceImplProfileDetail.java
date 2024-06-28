package org.sakaiproject.myo.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.entity.OkrUserProfileDetail;
import org.sakaiproject.myo.repository.UserRepositoryProfile;
import org.sakaiproject.myo.repository.UserRepositoryProfileDetail;
import org.sakaiproject.myo.service.UserServiceProfile;
import org.sakaiproject.myo.service.UserServiceProfileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplProfileDetail implements UserServiceProfileDetail{

	@Autowired
	UserRepositoryProfileDetail userRepo;

	@Override
	public List<OkrUserProfileDetail> findAll() {
		return userRepo.findAll();
	}

	@Override
	public UserRepositoryProfileDetail getRepo() {
		return userRepo;
	}
	

	@Transactional
	public void updateUserProfileDetail(String uuid, String profileImagePrivacy, String basicInfoPrivacy,
			String contactInfoPrivacy, String staffInfoPrivacy, String studentInfoPrivacy, String socialInfoPrivacy,
			String personalInfoPrivacy, Boolean showBirthYear, String viewConnectionsPrivacy,
			String viewOnlineStatusPrivacy, String viewStatusUpdatesPrivacy, String viewPicturesPrivacy,
			String sendMessagesPrivacy, String viewKudosRatingPrivacy, String emailNotificationsAdds,
			String emailNotificationsConnectionRequest, String emailNotificationsNewMessage,
			String emailNotificationsReplyMessage, String emailNotificationsAddWorksite,
			Boolean twitterIntegrationStatusUpdate, Boolean useGravatar, Boolean showKudosRating, Boolean showPictures,
			Boolean showOnlineStatus) {
		userRepo.updateUserProfileDetail(uuid, profileImagePrivacy, basicInfoPrivacy, contactInfoPrivacy,
				staffInfoPrivacy, studentInfoPrivacy, socialInfoPrivacy, personalInfoPrivacy, showBirthYear,
				viewConnectionsPrivacy, viewOnlineStatusPrivacy, viewStatusUpdatesPrivacy, viewPicturesPrivacy,
				sendMessagesPrivacy,  viewKudosRatingPrivacy,  emailNotificationsAdds,
				 emailNotificationsConnectionRequest,  emailNotificationsNewMessage,
				 emailNotificationsReplyMessage,  emailNotificationsAddWorksite,
				 twitterIntegrationStatusUpdate,  useGravatar,  showKudosRating,  showPictures,
				 showOnlineStatus);
	}
}

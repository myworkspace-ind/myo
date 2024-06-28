package org.sakaiproject.myo.service;

import java.util.List;

import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.entity.OkrUserProfileDetail;
import org.sakaiproject.myo.repository.UserRepositoryProfile;
import org.sakaiproject.myo.repository.UserRepositoryProfileDetail;
import org.springframework.data.repository.query.Param;

public interface UserServiceProfileDetail {
	public List<OkrUserProfileDetail> findAll();

	UserRepositoryProfileDetail getRepo();

	public void updateUserProfileDetail(String uuid, String profileImagePrivacy, String basicInfoPrivacy,
			String contactInfoPrivacy, String staffInfoPrivacy, String studentInfoPrivacy, String socialInfoPrivacy,
			String personalInfoPrivacy, Boolean showBirthYear, String viewConnectionsPrivacy,
			String viewOnlineStatusPrivacy, String viewStatusUpdatesPrivacy, String viewPicturesPrivacy,
			String sendMessagesPrivacy, String viewKudosRatingPrivacy, String emailNotificationsAdds,
			String emailNotificationsConnectionRequest, String emailNotificationsNewMessage,
			String emailNotificationsReplyMessage, String emailNotificationsAddWorksite,
			Boolean twitterIntegrationStatusUpdate, Boolean useGravatar, Boolean showKudosRating, Boolean showPictures,
			Boolean showOnlineStatus);

}

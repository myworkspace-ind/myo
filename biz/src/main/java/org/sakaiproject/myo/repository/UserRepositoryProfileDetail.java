package org.sakaiproject.myo.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.entity.OkrUserProfileDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryProfileDetail extends JpaRepository<OkrUserProfileDetail, UUID> {

	@Query("SELECT c FROM OkrUserProfileDetail c WHERE c.UUID = :uuid")
	OkrUserProfileDetail findByUUID(@Param("uuid") String uuid);

	@Transactional
	@Modifying
	@Query("UPDATE OkrUserProfileDetail u SET "
			+ "u.profileImagePrivacy = CASE WHEN :profileImagePrivacy IS NULL OR :profileImagePrivacy = 'null' THEN u.profileImagePrivacy ELSE :profileImagePrivacy END, "
			+ "u.basicInfoPrivacy = CASE WHEN :basicInfoPrivacy IS NULL OR :basicInfoPrivacy = 'null' THEN u.basicInfoPrivacy ELSE :basicInfoPrivacy END, "
			+ "u.contactInfoPrivacy = CASE WHEN :contactInfoPrivacy IS NULL OR :contactInfoPrivacy = 'null' THEN u.contactInfoPrivacy ELSE :contactInfoPrivacy END, "
			+ "u.staffInfoPrivacy = CASE WHEN :staffInfoPrivacy IS NULL OR :staffInfoPrivacy = 'null' THEN u.staffInfoPrivacy ELSE :staffInfoPrivacy END, "
			+ "u.studentInfoPrivacy = CASE WHEN :studentInfoPrivacy IS NULL OR :studentInfoPrivacy = 'null' THEN u.studentInfoPrivacy ELSE :studentInfoPrivacy END, "
			+ "u.socialInfoPrivacy = CASE WHEN :socialInfoPrivacy IS NULL OR :socialInfoPrivacy = 'null' THEN u.socialInfoPrivacy ELSE :socialInfoPrivacy END, "
			+ "u.personalInfoPrivacy = CASE WHEN :personalInfoPrivacy IS NULL OR :personalInfoPrivacy = 'null' THEN u.personalInfoPrivacy ELSE :personalInfoPrivacy END, "
			+ "u.showBirthYear = CASE WHEN :showBirthYear IS NULL THEN u.showBirthYear ELSE :showBirthYear END, "
			+ "u.viewConnectionsPrivacy = CASE WHEN :viewConnectionsPrivacy IS NULL OR :viewConnectionsPrivacy = 'null' THEN u.viewConnectionsPrivacy ELSE :viewConnectionsPrivacy END, "
			+ "u.viewOnlineStatusPrivacy = CASE WHEN :viewOnlineStatusPrivacy IS NULL OR :viewOnlineStatusPrivacy = 'null' THEN u.viewOnlineStatusPrivacy ELSE :viewOnlineStatusPrivacy END, "
			+ "u.viewStatusUpdatesPrivacy = CASE WHEN :viewStatusUpdatesPrivacy IS NULL OR :viewStatusUpdatesPrivacy = 'null' THEN u.viewStatusUpdatesPrivacy ELSE :viewStatusUpdatesPrivacy END, "
			+ "u.viewPicturesPrivacy = CASE WHEN :viewPicturesPrivacy IS NULL OR :viewPicturesPrivacy = 'null' THEN u.viewPicturesPrivacy ELSE :viewPicturesPrivacy END, "
			+ "u.sendMessagesPrivacy = CASE WHEN :sendMessagesPrivacy IS NULL OR :sendMessagesPrivacy = 'null' THEN u.sendMessagesPrivacy ELSE :sendMessagesPrivacy END, "
			+ "u.viewKudosRatingPrivacy = CASE WHEN :viewKudosRatingPrivacy IS NULL OR :viewKudosRatingPrivacy = 'null' THEN u.viewKudosRatingPrivacy ELSE :viewKudosRatingPrivacy END, "
			+ "u.emailNotificationsAdds = CASE WHEN :emailNotificationsAdds IS NULL OR :emailNotificationsAdds = 'null' THEN u.emailNotificationsAdds ELSE :emailNotificationsAdds END, "
			+ "u.emailNotificationsConnectionRequest = CASE WHEN :emailNotificationsConnectionRequest IS NULL OR :emailNotificationsConnectionRequest = 'null' THEN u.emailNotificationsConnectionRequest ELSE :emailNotificationsConnectionRequest END, "
			+ "u.emailNotificationsNewMessage = CASE WHEN :emailNotificationsNewMessage IS NULL OR :emailNotificationsNewMessage = 'null' THEN u.emailNotificationsNewMessage ELSE :emailNotificationsNewMessage END, "
			+ "u.emailNotificationsReplyMessage = CASE WHEN :emailNotificationsReplyMessage IS NULL OR :emailNotificationsReplyMessage = 'null' THEN u.emailNotificationsReplyMessage ELSE :emailNotificationsReplyMessage END, "
			+ "u.emailNotificationsAddWorksite = CASE WHEN :emailNotificationsAddWorksite IS NULL OR :emailNotificationsAddWorksite = 'null' THEN u.emailNotificationsAddWorksite ELSE :emailNotificationsAddWorksite END, "
			+ "u.twitterIntegrationStatusUpdate = CASE WHEN :twitterIntegrationStatusUpdate IS NULL THEN u.twitterIntegrationStatusUpdate ELSE :twitterIntegrationStatusUpdate END, "
			+ "u.useGravatar = CASE WHEN :useGravatar IS NULL THEN u.useGravatar ELSE :useGravatar END, "
			+ "u.showKudosRating = CASE WHEN :showKudosRating IS NULL THEN u.showKudosRating ELSE :showKudosRating END, "
			+ "u.showPictures = CASE WHEN :showPictures IS NULL THEN u.showPictures ELSE :showPictures END, "
			+ "u.showOnlineStatus = CASE WHEN :showOnlineStatus IS NULL THEN u.showOnlineStatus ELSE :showOnlineStatus END "
			+ "WHERE u.UUID = :uuid")
	void updateUserProfileDetail(@Param("uuid") String uuid, @Param("profileImagePrivacy") String profileImagePrivacy,
			@Param("basicInfoPrivacy") String basicInfoPrivacy, @Param("contactInfoPrivacy") String contactInfoPrivacy,
			@Param("staffInfoPrivacy") String staffInfoPrivacy, @Param("studentInfoPrivacy") String studentInfoPrivacy,
			@Param("socialInfoPrivacy") String socialInfoPrivacy,
			@Param("personalInfoPrivacy") String personalInfoPrivacy, @Param("showBirthYear") Boolean showBirthYear,
			@Param("viewConnectionsPrivacy") String viewConnectionsPrivacy,
			@Param("viewOnlineStatusPrivacy") String viewOnlineStatusPrivacy,
			@Param("viewStatusUpdatesPrivacy") String viewStatusUpdatesPrivacy,
			@Param("viewPicturesPrivacy") String viewPicturesPrivacy,
			@Param("sendMessagesPrivacy") String sendMessagesPrivacy,
			@Param("viewKudosRatingPrivacy") String viewKudosRatingPrivacy,
			@Param("emailNotificationsAdds") String emailNotificationsAdds,
			@Param("emailNotificationsConnectionRequest") String emailNotificationsConnectionRequest,
			@Param("emailNotificationsNewMessage") String emailNotificationsNewMessage,
			@Param("emailNotificationsReplyMessage") String emailNotificationsReplyMessage,
			@Param("emailNotificationsAddWorksite") String emailNotificationsAddWorksite,
			@Param("twitterIntegrationStatusUpdate") Boolean twitterIntegrationStatusUpdate,
			@Param("useGravatar") Boolean useGravatar, @Param("showKudosRating") Boolean showKudosRating,
			@Param("showPictures") Boolean showPictures, @Param("showOnlineStatus") Boolean showOnlineStatus);
}

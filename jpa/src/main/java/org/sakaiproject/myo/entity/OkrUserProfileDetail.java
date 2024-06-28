package org.sakaiproject.myo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "okr_users_info")
@NamedQuery(name = "OkrUserProfileDetail.findAll", query = "SELECT o FROM OkrUserProfileDetail o")
@Getter
@Setter
public class OkrUserProfileDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String UUID;

	@Column(name = "profile_image_privacy")
	private String profileImagePrivacy;

	@Column(name = "basic_info_privacy")
	private String basicInfoPrivacy;

	@Column(name = "contact_info_privacy")
	private String contactInfoPrivacy;

	@Column(name = "staff_info_privacy")
	private String staffInfoPrivacy;

	@Column(name = "student_info_privacy")
	private String studentInfoPrivacy;

	@Column(name = "social_info_privacy")
	private String socialInfoPrivacy;

	@Column(name = "personal_info_privacy")
	private String personalInfoPrivacy;

	@Column(name = "show_birth_year")
	private boolean showBirthYear;

	@Column(name = "view_connections_privacy")
	private String viewConnectionsPrivacy;

	@Column(name = "view_online_status_privacy")
	private String viewOnlineStatusPrivacy;

	@Column(name = "view_status_updates_privacy")
	private String viewStatusUpdatesPrivacy;

	@Column(name = "view_pictures_privacy")
	private String viewPicturesPrivacy;

	@Column(name = "send_messages_privacy")
	private String sendMessagesPrivacy;

	@Column(name = "view_kudos_rating_privacy")
	private String viewKudosRatingPrivacy;

	@Column(name = "email_notifications_adds")
	private String emailNotificationsAdds;

	@Column(name = "email_notifications_connection_request")
	private String emailNotificationsConnectionRequest;

	@Column(name = "email_notifications_new_message")
	private String emailNotificationsNewMessage;

	@Column(name = "email_notifications_reply_message")
	private String emailNotificationsReplyMessage;

	@Column(name = "email_notifications_add_worksite")
	private String emailNotificationsAddWorksite;

	@Column(name = "twitter_integration_status_update")
	private Boolean twitterIntegrationStatusUpdate;

	@Column(name = "use_gravatar")
	private boolean useGravatar;

	@Column(name = "show_kudos_rating")
	private boolean showKudosRating;

	@Column(name = "show_pictures")
	private boolean showPictures;

	@Column(name = "show_online_status")
	private boolean showOnlineStatus;

}

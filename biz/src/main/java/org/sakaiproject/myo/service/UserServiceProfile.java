package org.sakaiproject.myo.service;

import java.util.Date;
import java.util.List;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.repository.UserRepository;
import org.sakaiproject.myo.repository.UserRepositoryProfile;

public interface UserServiceProfile {
	public List<OkrUserProfile> findAll();

	UserRepositoryProfile getRepo();

	public void updateUserProfile(String email, String description, String name, String address, String pronouns,
			String phoneticPronunciation, String commonName, String Email, String homepage, String workphone,
			String homephone, String mobilephone, String facsimile, String position, String department, String school,
			String room, String staffprofile, String universityprofileURL, String academicresearchURL,
			String publicationsandconferences, String course, String subjects, String books, String shows,
			String movies, String quotes);
}

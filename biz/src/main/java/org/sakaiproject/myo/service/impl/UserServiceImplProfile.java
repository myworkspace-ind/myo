package org.sakaiproject.myo.service.impl;

import java.util.Date;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.sakaiproject.myo.*;
import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.repository.UserRepository;
import org.sakaiproject.myo.repository.UserRepositoryProfile;
import org.sakaiproject.myo.service.UserService;
import org.sakaiproject.myo.service.UserServiceProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImplProfile implements UserServiceProfile {

	@Autowired
	UserRepositoryProfile userRepo;

	@Override
	public List<OkrUserProfile> findAll() {
		return userRepo.findAll();
	}

	@Override
	public UserRepositoryProfile getRepo() {
		return userRepo;
	}

	@Transactional
	public void updateUserProfile(String email, String description, String name, String address, String pronouns,
			String phoneticPronunciation, String commonName, String Email, String homepage, String workphone,
			String homephone, String mobilephone, String facsimile, String position, String department, String school,
			String room, String staffprofile, String universityprofileURL, String academicresearchURL,
			String publicationsandconferences, String course, String subjects, String books, String shows,
			String movies, String quotes) {
		userRepo.updateUserProfile(email, description, name, address, pronouns, phoneticPronunciation, commonName,
				Email, homepage, workphone, homephone, mobilephone, facsimile, position, department, school, room,
				staffprofile, universityprofileURL, academicresearchURL, publicationsandconferences, course, subjects,
				books, shows, movies, quotes);
	}

}

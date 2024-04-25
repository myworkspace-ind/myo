package org.sakaiproject.myo.service;

import java.util.List;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.entity.OkrUserProfile;
import org.sakaiproject.myo.repository.UserRepository;
import org.sakaiproject.myo.repository.UserRepositoryProfile;

public interface UserServiceProfile {
	public List<OkrUserProfile> findAll();
	UserRepositoryProfile getRepo();
	public void updateUserProfile(String email, String description, String nickname) ;

}

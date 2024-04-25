package org.sakaiproject.myo.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
	public void updateUserProfile(String email, String description, String nickname) {
		userRepo.updateUserProfile(email, description, nickname);
	}

}

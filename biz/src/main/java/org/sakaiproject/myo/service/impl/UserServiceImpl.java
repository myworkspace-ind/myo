package org.sakaiproject.myo.service.impl;

import java.util.List;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.repository.UserRepository;
import org.sakaiproject.myo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Override
	public List<OkrUser> findAll() {
		return userRepo.findAll();
	}

	@Override
	public UserRepository getRepo() {
		return userRepo;
	}

}

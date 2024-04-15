package org.sakaiproject.myo.service;

import java.util.List;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.repository.UserRepository;

public interface UserService {
	public List<OkrUser> findAll();
	UserRepository getRepo();
}

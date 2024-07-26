package org.sakaiproject.myo.service;

import java.util.List;
import java.util.UUID;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.repository.UserRepository;

public interface UserService {
	public List<OkrUser> findAll();
	UserRepository getRepo();
	String findManagerEmailByUserId(UUID UserId);
	UUID findManagerIdByUserId(UUID UserId);
}

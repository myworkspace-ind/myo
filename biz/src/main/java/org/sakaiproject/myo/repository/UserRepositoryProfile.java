package org.sakaiproject.myo.repository;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.entity.OkrUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryProfile extends JpaRepository<OkrUserProfile, UUID> {

	@Query("select c from OkrUserProfile c where c.LAST_MODIFIED_BY = :name")
	OkrUserProfile findByEmail(@Param("name") String name);

	@Transactional
	@Modifying
	@Query("UPDATE OkrUserProfile u SET u.DESCRIPTION = :description, u.NICKNAME = :nickname WHERE u.LAST_MODIFIED_BY = :email")
	void updateUserProfile(@Param("email") String email, @Param("description") String description,
			@Param("nickname") String nickname);
}

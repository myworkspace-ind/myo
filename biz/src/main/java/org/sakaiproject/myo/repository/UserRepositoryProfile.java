package org.sakaiproject.myo.repository;

import java.util.UUID;

import org.sakaiproject.myo.entity.OkrUser;
import org.sakaiproject.myo.entity.OkrUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryProfile extends JpaRepository<OkrUserProfile, UUID> {

	@Query("select c from OkrUserProfile c where c.LAST_MODIFIED_BY = :name")
	OkrUserProfile findByEmail(@Param("name") String name);

}

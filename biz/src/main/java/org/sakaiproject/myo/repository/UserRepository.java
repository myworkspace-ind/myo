package org.sakaiproject.myo.repository;

import java.util.UUID;

import org.sakaiproject.myo.entity.OkrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<OkrUser, UUID> {

	@Query("select c from OkrUser c where c.email = :name")
	OkrUser findByEmail(@Param("name") String name);

}

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
	// findByEmail auto generated and perform the query in @Query
	OkrUser findByEmail(@Param("name") String name);

	@Query(value="SELECT u.manager_email FROM OkrUser u WHERE u.user_id = :user_id", nativeQuery=true)
	String findManagerEmailByUserId(@Param("user_id") UUID userId);
	
	@Query(value="SELECT u.manager_id FROM OkrUser u WHERE u.user_id = :user_id", nativeQuery=true)
	UUID findManagerIdByUserId(@Param("user_id") UUID userId);
}

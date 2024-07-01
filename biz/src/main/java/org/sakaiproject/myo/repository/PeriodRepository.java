package org.sakaiproject.myo.repository;

import java.util.UUID;

import org.sakaiproject.myo.entity.OkrPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<OkrPeriod, UUID> {

	@Query("select c from OkrPeriod c where c.name = :name")
	OkrPeriod findByName(@Param("name") String name);

}
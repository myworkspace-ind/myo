package org.sakaiproject.myo.repository;

import java.util.List;
import java.util.UUID;

import org.sakaiproject.myo.entity.OkrObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OkrRepository extends JpaRepository<OkrObj, UUID> {

	@Query(value = "SELECT id as id,obj_id,name as name,itype,criterias,self_grade,grade,duedate,create_by,last_modified,last_modified_by,linked_to_id,progress,created_at,completed_date,target,linked_to_kr,linked_by,comment_link,note,milestone,progress_milestone,kr_weight FROM okr_krs\n"
			+ "WHERE obj_id IN (\n" + "SELECT id FROM okr_objs\n" + "WHERE user_id in \n" + "(\n"
			+ "SELECT user_id FROM okr_users\n" + "WHERE email = 'hong.bui@itd.com.vn'\n" + ")\n"
			+ ")", nativeQuery = true)
	List<Object[]> findKrByEmail(@Param("email") String email);

//    @Query(value = "SELECT id, org_id, user_id ,period_id,name,review_date,create_by,last_modified,last_modified_by,is_custom_progress,custom_progress,linked_to_id,created_at,src_obj_id,comment,okr_id,status,weight FROM okr_objs\n"
//    		+ "WHERE user_id in \n"
//    		+ "(\n"
//    		+ "SELECT user_id FROM okr_users\n"
//    		+ "WHERE email = 'hong.bui@itd.com.vn'\n"
//    		+ ")", nativeQuery = true)
//    List<Object[]> findObjByEmail(@Param("email") String email);

//    @Query(value = "SELECT CAST(id as varchar) as id, CAST(org_id as varchar) as \"orgId\", CAST(user_id as varchar) as \"userId\", CAST(period_id as varchar) as \"periodId\", name as name, review_date as \"reviewDate\", CAST(create_by as varchar) as \"createBy\", last_modified as \"lastModified\",CAST (last_modified_by as varchar) as \"lastModifiedBy\",is_custom_progress as \"isCustomProgress\",custom_progress as \"customProgress\",CAST(linked_to_id as varchar) as linkedToId,created_at as \"createdAt\",CAST(src_obj_id as varchar) as \"srcObjId\",comment as comment,CAST(okr_id as varchar) as \"okrId\",status as status,weight as weight FROM okr_objs\n"
//    		+ "WHERE user_id in \n"
//    		+ "(\n"
//    		+ "SELECT user_id FROM okr_users\n"
//    		+ "WHERE email = 'hong.bui@itd.com.vn'\n"
//    		+ ")",  nativeQuery = true)
//    List<Object[]> findObjByEmail(@Param("email") String email);

	// Full column
//    @Query(value = "SELECT CAST(id as varchar), CAST(org_id as varchar), CAST(user_id as varchar), CAST(period_id as varchar), name, review_date, CAST(create_by as varchar), last_modified,CAST(last_modified_by as varchar), is_custom_progress, custom_progress, CAST(linked_to_id as varchar),created_at, CAST(src_obj_id as varchar), comment, CAST(okr_id as varchar), status,weight FROM okr_objs\n"
//    		+ "WHERE user_id in \n"
//    		+ "(\n"
//    		+ "SELECT user_id FROM okr_users\n"
//    		+ "WHERE email = 'hong.bui@itd.com.vn'\n"
//    		+ ")",  nativeQuery = true)
//    List<Object[]> findObjByEmail(@Param("email") String email);

	@Query(value = "SELECT p.name as \"Kỳ\", g.name as \"BP\", u.name as \"Họ tên\", u.email as \"Email\", o.name as \"Objective\", k.name as \"Keyresult\", k.itype as \"Type\", k.duedate, k.target, k.kr_weight, k.progress, k.completed_date, k.self_grade, k.grade  FROM \"okr_orgs\" g\n"
			+ "LEFT JOIN \"okr_users\" u ON g.id = u.org_id\n" + "LEFT JOIN \"okr_objs\" o on o.user_id = u.user_id\n"
			+ "LEFT JOIN \"okr_krs\" k ON o.id = k.obj_id\n" + "LEFT JOIN \"okr_periods\" p ON o.period_id = p.id \n"
			+ "WHERE u.email in :emails\n"
			+ "ORDER BY p.name, g.name, u.name, o.name, kr_weight, k.name", nativeQuery = true)
	List<Object[]> findObjByEmail(@Param("emails") String[] emails);

	@Modifying
	@Query(value = "insert into okr_objs (id ,comment ,created_at ,custom_progress ,is_custom_progress ,last_modified ,name ,okr_id ,review_date ,src_obj_id ,status ,weight ,linked_to_id ,org_id ,period_id ,create_by ,last_modified_by ,user_id ) VALUES \r\n "
	+ "(uuid() /*not nullable*/,null   ,{ts '2024-04-24 16:23:16.'},0              ,0                 ,{d '2024-04-24'},null,null  ,{d '2024-04-24'},null      ,null  ,0     ,null        ,null  ,null     ,null     ,null            ,null   )\r\n"
	, nativeQuery = true)
	void insertOkr();

}

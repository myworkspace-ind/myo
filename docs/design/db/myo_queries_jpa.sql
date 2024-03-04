SELECT CAST(id as varchar) as id, CAST(org_id as varchar) as "orgId", CAST(user_id as varchar) as "userId", CAST(period_id as varchar) as "periodId", name as name, review_date as "reviewDate", CAST(create_by as varchar) as "createBy", last_modified as "lastModified",CAST (last_modified_by as varchar) as "lastModifiedBy",is_custom_progress as "isCustomProgress",custom_progress as "customProgress",CAST(linked_to_id as varchar) as linkedToId,created_at as "createdAt",CAST(src_obj_id as varchar) as "srcObjId",comment as comment,CAST(okr_id as varchar) as "okrId",status as status,weight as weight FROM okr_objs
WHERE user_id in 
(
SELECT user_id FROM okr_users
WHERE email = 'hong.bui@itd.com.vn'
)


-- 2: Remote as...
SELECT CAST(id as varchar), CAST(org_id as varchar), CAST(user_id as varchar), CAST(period_id as varchar), name, review_date, CAST(create_by as varchar), last_modified,CAST(last_modified_by as varchar), is_custom_progress, custom_progress, CAST(linked_to_id as varchar),created_at, CAST(src_obj_id as varchar), comment, CAST(okr_id as varchar), status,weight FROM okr_objs
WHERE user_id in 
(
SELECT user_id FROM okr_users
WHERE email = 'hong.bui@itd.com.vn'
)
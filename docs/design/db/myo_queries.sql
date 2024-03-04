-- 1: User info
SELECT "user_id","manager_id","org_id","email","manager_email","name","role","department","numobjs","start_date","end_date" FROM "public"."okr_users"
WHERE email = 'tien.bui@itd.com.vn'

-- 2: Manager info
SELECT "user_id","manager_id","org_id","email","manager_email","name","role","department","numobjs","start_date","end_date" FROM "public"."okr_users"
WHERE manager_id in (
SELECT "manager_id" FROM "public"."okr_users"
WHERE email = 'tien.bui@itd.com.vn'
)

-- 3: Org info
SELECT "id","name","org_id","manager_id","imp_name" FROM "public"."okr_orgs"
WHERE id in (
SELECT "org_id" FROM "public"."okr_users"
WHERE email = 'tien.bui@itd.com.vn'
)

-- 4: Manager of Org
SELECT "user_id","manager_id","org_id","email","manager_email","name","role","department","numobjs","start_date","end_date" FROM "public"."okr_users"
WHERE "manager_id" IN
(
SELECT "manager_id" FROM "public"."okr_orgs"
WHERE id in (
SELECT "org_id" FROM "public"."okr_users"
WHERE email = 'tien.bui@itd.com.vn'
)
)

--
SELECT "user_id","manager_id","org_id","email","manager_email","name","role","department","numobjs","start_date","end_date" FROM "public"."okr_users"
WHERE "manager_id" IN
(
SELECT "user_id" FROM "public"."okr_delegate_users"
WHERE org_id in (
SELECT "org_id" FROM "public"."okr_users"
WHERE email = 'tien.bui@itd.com.vn'
)
)



-- 5: Objectives

SELECT "id","org_id","user_id","period_id","name","review_date","create_by","last_modified","last_modified_by","is_custom_progress","custom_progress","linked_to_id","created_at","src_obj_id","comment","okr_id","status","weight" FROM "public"."okr_objs"
WHERE user_id in 
(
SELECT "user_id" FROM "public"."okr_users"
WHERE email = 'tien.bui@itd.com.vn'
)

-- 6: Keyresult

SELECT id,obj_id,name,itype,criterias,self_grade,grade,duedate,create_by,last_modified,last_modified_by,linked_to_id,progress,created_at,completed_date,target,linked_to_kr,linked_by,comment_link,note,milestone,progress_milestone,kr_weight FROM okr_krs
WHERE obj_id IN (

SELECT id FROM okr_objs
WHERE user_id in 
(
SELECT user_id FROM okr_users
WHERE email = 'hong.bui@itd.com.vn'
)

)




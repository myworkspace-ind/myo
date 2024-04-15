-- drop foreign key constraint
--
ALTER TABLE IF EXISTS archive.okr_orgs DROP CONSTRAINT IF EXISTS fk_users_orgs;

insert into archive.okr_orgs
select * 
from public.okr_orgs;

insert into archive.okr_users
select * 
from public.okr_users;

--
ALTER TABLE ONLY archive.okr_orgs
    ADD CONSTRAINT fk_users_orgs FOREIGN KEY (manager_id) REFERENCES archive.okr_users(user_id);

insert into archive.okr_delegate_users
select * 
from public.okr_delegate_users;



---- Transfer objectives of 2 users by email
insert into archive.okr_objs
select * 
from public.okr_objs
WHERE user_id in 
(
SELECT "user_id" FROM "public"."okr_users"
WHERE email in ('tien.bui@itd.com.vn', 'phuc.bui@itd.com.vn')
)

-- Transfer keyresult of 2 users by email
insert into archive.okr_krs
select * 
from public.okr_krs

WHERE "obj_id" IN (

SELECT "id" FROM "public"."okr_objs"
WHERE user_id in 
(
SELECT "user_id" FROM "public"."okr_users"
WHERE email in ('tien.bui@itd.com.vn', 'phuc.bui@itd.com.vn')
)

)

---- Transfer objectives of 2 users by email.END

-- Delete transfer data

DELETE 
from public.okr_krs

WHERE "obj_id" IN (

SELECT "id" FROM "public"."okr_objs"
WHERE user_id in 
(
SELECT "user_id" FROM "public"."okr_users"
WHERE email in ('tien.bui@itd.com.vn', 'phuc.bui@itd.com.vn')
)

)

--- Delete objs
DELETE 
from public.okr_objs
WHERE user_id in 
(
SELECT "user_id" FROM "public"."okr_users"
WHERE email in ('tien.bui@itd.com.vn', 'phuc.bui@itd.com.vn')
)

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
	    @Query("UPDATE OkrUserProfile u SET "
	            + "u.DESCRIPTION = CASE WHEN :description IS NULL OR :description = 'null' THEN u.DESCRIPTION ELSE :description END, "
	            + "u.DISPLAY_NAME = CASE WHEN :Name IS NULL OR :Name = 'null' THEN u.DISPLAY_NAME ELSE :Name END, "
	            + "u.POSTAL_ADDRESS = CASE WHEN :Address IS NULL OR :Address = 'null' THEN u.POSTAL_ADDRESS ELSE :Address END, "
	            + "u.PRONOUNS = CASE WHEN :Pronouns IS NULL OR :Pronouns = 'null' THEN u.PRONOUNS ELSE :Pronouns END, "
	            + "u.PHONETIC_PRONUNCIATION = CASE WHEN :PhoneticPronunciation IS NULL OR :PhoneticPronunciation = 'null' THEN u.PHONETIC_PRONUNCIATION ELSE :PhoneticPronunciation END, "
	            + "u.COMMON_NAME = CASE WHEN :CommonName IS NULL OR :CommonName = 'null' THEN u.COMMON_NAME ELSE :CommonName END, "
	            + "u.MAIL = CASE WHEN :Email IS NULL OR :Email = 'null' THEN u.MAIL ELSE :Email END, "
	            + "u.HOME_POSTAL_ADDRESS = CASE WHEN :Homepage IS NULL OR :Homepage = 'null' THEN u.HOME_POSTAL_ADDRESS ELSE :Homepage END, "
	            + "u.TELEPHONE_NUMBER = CASE WHEN :Workphone IS NULL OR :Workphone = 'null' THEN u.TELEPHONE_NUMBER ELSE :Workphone END, "
	            + "u.HOME_PHONE = CASE WHEN :Homephone IS NULL OR :Homephone = 'null' THEN u.HOME_PHONE ELSE :Homephone END, "
	            + "u.MOBILE = CASE WHEN :Mobilephone IS NULL OR :Mobilephone = 'null' THEN u.MOBILE ELSE :Mobilephone END, "
	            + "u.FAX_NUMBER = CASE WHEN :Facsimile IS NULL OR :Facsimile = 'null' THEN u.FAX_NUMBER ELSE :Facsimile END, "
	            + "u.EMPLOYEE_TYPE = CASE WHEN :Position IS NULL OR :Position = 'null' THEN u.EMPLOYEE_TYPE ELSE :Position END, "
	            + "u.DEPARTMENT_NUMBER = CASE WHEN :Department IS NULL OR :Department = 'null' THEN u.DEPARTMENT_NUMBER ELSE :Department END, "
	            + "u.ORGANIZATION = CASE WHEN :School IS NULL OR :School = 'null' THEN u.ORGANIZATION ELSE :School END, "
	            + "u.ROOM_NUMBER = CASE WHEN :Room IS NULL OR :Room = 'null' THEN u.ROOM_NUMBER ELSE :Room END, "
	            + "u.STAFF_PROFILE = CASE WHEN :Staffprofile IS NULL OR :Staffprofile = 'null' THEN u.STAFF_PROFILE ELSE :Staffprofile END, "
	            + "u.UNIVERSITY_PROFILE_URL = CASE WHEN :UniversityprofileURL IS NULL OR :UniversityprofileURL = 'null' THEN u.UNIVERSITY_PROFILE_URL ELSE :UniversityprofileURL END, "
	            + "u.ACADEMIC_PROFILE_URL = CASE WHEN :AcademicresearchURL IS NULL OR :AcademicresearchURL = 'null' THEN u.ACADEMIC_PROFILE_URL ELSE :AcademicresearchURL END, "
	            + "u.PUBLICATIONS = CASE WHEN :Publicationsandconferences IS NULL OR :Publicationsandconferences = 'null' THEN u.PUBLICATIONS ELSE :Publicationsandconferences END, "
	            + "u.EDUCATION_COURSE = CASE WHEN :Course IS NULL OR :Course = 'null' THEN u.EDUCATION_COURSE ELSE :Course END, "
	            + "u.EDUCATION_SUBJECTS = CASE WHEN :Subjects IS NULL OR :Subjects = 'null' THEN u.EDUCATION_SUBJECTS ELSE :Subjects END, "
	            + "u.FAVOURITE_BOOKS = CASE WHEN :books IS NULL OR :books = 'null' THEN u.FAVOURITE_BOOKS ELSE :books END, "
	            + "u.FAVOURITE_TV_SHOWS = CASE WHEN :shows IS NULL OR :shows = 'null' THEN u.FAVOURITE_TV_SHOWS ELSE :shows END, "
	            + "u.FAVOURITE_MOVIES = CASE WHEN :movies IS NULL OR :movies = 'null' THEN u.FAVOURITE_MOVIES ELSE :movies END, "
	            + "u.FAVOURITE_QUOTES = CASE WHEN :quotes IS NULL OR :quotes = 'null' THEN u.FAVOURITE_QUOTES ELSE :quotes END "
	            + "WHERE u.LAST_MODIFIED_BY = :email")
	    void updateUserProfile(@Param("email") String email, @Param("description") String description,
	            @Param("Name") String Name, @Param("Address") String Address, @Param("Pronouns") String Pronouns,
	            @Param("PhoneticPronunciation") String PhoneticPronunciation, @Param("CommonName") String CommonName,
	            @Param("Email") String Email, @Param("Homepage") String Homepage, @Param("Workphone") String Workphone,
	            @Param("Homephone") String Homephone, @Param("Mobilephone") String Mobilephone,
	            @Param("Facsimile") String Facsimile, @Param("Position") String Position,
	            @Param("Department") String Department, @Param("School") String School, @Param("Room") String Room,
	            @Param("Staffprofile") String Staffprofile, @Param("UniversityprofileURL") String UniversityprofileURL,
	            @Param("AcademicresearchURL") String AcademicresearchURL,
	            @Param("Publicationsandconferences") String Publicationsandconferences, @Param("Course") String Course,
	            @Param("Subjects") String Subjects, @Param("books") String books, @Param("shows") String shows,
	            @Param("movies") String movies, @Param("quotes") String quotes);

}

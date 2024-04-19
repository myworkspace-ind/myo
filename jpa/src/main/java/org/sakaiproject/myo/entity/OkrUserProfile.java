package org.sakaiproject.myo.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The persistent class for the okr_users database table.
 * 
 */
@Entity
@Table(name = "SAKAI_PERSON_T")
@NamedQuery(name = "SAKAI_PERSON_T.findAll", query = "SELECT o FROM SAKAI_PERSON_T o")
public class OkrUserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy=GenerationType.AUTO)
	 * 
	 * @Column(name="user_id") private UUID userId;
	 * 
	 * private String department;
	 * 
	 * private String email;
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @Column(name="end_date") private Date endDate;
	 * 
	 * @Column(name="manager_email") private String managerEmail;
	 * 
	 * private String name;
	 * 
	 * private Integer numobjs;
	 * 
	 * private String role;
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @Column(name="start_date") private Date startDate;
	 * 
	 * //bi-directional many-to-one association to OkrDelegateUser
	 * 
	 * @OneToMany(mappedBy="okrUser") private List<OkrDelegateUser>
	 * okrDelegateUsers;
	 * 
	 * //bi-directional many-to-one association to OkrKr
	 * 
	 * @OneToMany(mappedBy="okrUser1") private List<OkrKr> okrKrs1;
	 * 
	 * //bi-directional many-to-one association to OkrKr
	 * 
	 * @OneToMany(mappedBy="okrUser2") private List<OkrKr> okrKrs2;
	 * 
	 * //bi-directional many-to-one association to OkrObj
	 * 
	 * @OneToMany(mappedBy="okrUser1") private List<OkrObj> okrObjs1;
	 * 
	 * //bi-directional many-to-one association to OkrObj
	 * 
	 * @OneToMany(mappedBy="okrUser2") private List<OkrObj> okrObjs2;
	 * 
	 * //bi-directional many-to-one association to OkrObj
	 * 
	 * @OneToMany(mappedBy="okrUser3") private List<OkrObj> okrObjs3;
	 * 
	 * //bi-directional many-to-one association to OkrOrg
	 * 
	 * @OneToMany(mappedBy="okrUser") private List<OkrOrg> okrOrgs;
	 * 
	 * //bi-directional many-to-one association to OkrOrg
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="org_id") private OkrOrg okrOrg;
	 * 
	 * //bi-directional many-to-one association to OkrUser
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="manager_id") private OkrUser okrUser;
	 * 
	 * //bi-directional many-to-one association to OkrUser
	 * 
	 * @OneToMany(mappedBy="okrUser") private List<OkrUser> okrUsers;
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID ID;
	private int VERSION;
	private String PERSON_TYPE;
	private String UUID;
	private String LAST_MODIFIED_BY;
	private String LAST_MODIFIED_DATE;
	private String CREATED_BY;
	private String CREATED_DATE;
	private String AGENT_UUID;
	private String TYPE_UUID;
	private String COMMON_NAME;
	private String DESCRIPTION;
	private String SEE_ALSO;
	private String STREET;
	private String SURNAME;
	private String TELEPHONE_NUMBER;
	private String FAX_NUMBER;
	private String LOCALITY_NAME;
	private String OU;
	private String PHYSICAL_DELIVERY_OFFICE_NAME;
	private String POSTAL_ADDRESS;
	private String POSTAL_CODE;
	private String POST_OFFICE_BOX;
	private String STATE_PROVINCE_NAME;
	private String STREET_ADDRESS;
	private String TITLE;
	private String BUSINESS_CATEGORY;
	private String CAR_LICENSE;
	private String DEPARTMENT_NUMBER;
	private String DISPLAY_NAME;
	private String EMPLOYEE_NUMBER;
	private String EMPLOYEE_TYPE;
	private String GIVEN_NAME;
	private String HOME_PHONE;
	private String HOME_POSTAL_ADDRESS;
	private String INITIALS;
	private String JPEG_PHOTO;
	private String LABELED_URI;
	private String MAIL;
	private String MANAGER;
	private String MOBILE;
	private String ORGANIZATION;
	private String PAGER;
	private String PREFERRED_LANGUAGE;
	private String ROOM_NUMBER;
	private String SECRETARY;
	private String UID_C;
	private String USER_CERTIFICATE;
	private String USER_PKCS12;
	private String USER_SMIME_CERTIFICATE;
	private String X500_UNIQUE_ID;
	private String AFFILIATION;
	private String ENTITLEMENT;
	private String NICKNAME;
	private String ORG_DN;
	private String ORG_UNIT_DN;
	private String PRIMARY_AFFILIATION;
	private String PRIMARY_ORG_UNIT_DN;
	private String PRINCIPAL_NAME;
	private String PRINCIPAL_NAME_PRIOR;
	private String SCOPED_AFFILIATION;
	private String TARGETED_ID;
	private String ASSURANCE;
	private String UNIQUE_ID;
	private String ORCID;
	private String CAMPUS;
	private String HIDE_PRIVATE_INFO;
	private String HIDE_PUBLIC_INFO;
	private String NOTES;
	private String PICTURE_URL;
	private String SYSTEM_PICTURE_PREFERRED;
	private String ferpaEnabled;
	private Date dateOfBirth;
	private String locked;
	private String FAVOURITE_BOOKS;
	private String FAVOURITE_TV_SHOWS;
	private String FAVOURITE_MOVIES;
	private String FAVOURITE_QUOTES;
	private String EDUCATION_COURSE;
	private String EDUCATION_SUBJECTS;
	private String NORMALIZEDMOBILE;
	private String STAFF_PROFILE;
	private String UNIVERSITY_PROFILE_URL;
	private String ACADEMIC_PROFILE_URL;
	private String PUBLICATIONS;
	private String BUSINESS_BIOGRAPHY;
	private String PHONETIC_PRONUNCIATION;
	private String PRONOUNS;

	public OkrUserProfile() {
	}

	public UUID getId() {
		return this.ID;
	}

	public void setId(UUID ID) {
		this.ID = ID;
	}

	public int getVersion() {
		return this.VERSION;
	}

	public void setVersion(int VERSION) {
		this.VERSION = VERSION;
	}

	public String getPerson_type() {
		return this.PERSON_TYPE;
	}

	public void setPerson_type(String PERSON_TYPE) {
		this.PERSON_TYPE = PERSON_TYPE;
	}

	public String getUuid() {
		return this.UUID;
	}

	public void setUuid(String UUID) {
		this.UUID = UUID;
	}

	public String getLast_modified_by() {
		return this.LAST_MODIFIED_BY;
	}

	public void setLast_modified_by(String LAST_MODIFIED_BY) {
		this.LAST_MODIFIED_BY = LAST_MODIFIED_BY;
	}

	public String getLast_modified_date() {
		return this.LAST_MODIFIED_DATE;
	}

	public void setLast_modified_date(String LAST_MODIFIED_DATE) {
		this.LAST_MODIFIED_DATE = LAST_MODIFIED_DATE;
	}

	public String getCreated_by() {
		return this.CREATED_BY;
	}

	public void setCreated_by(String CREATED_BY) {
		this.CREATED_BY = CREATED_BY;
	}

	public String getCreated_date() {
		return this.CREATED_DATE;
	}

	public void setCreated_date(String CREATED_DATE) {
		this.CREATED_DATE = CREATED_DATE;
	}

	public String getAgent_uuid() {
		return this.AGENT_UUID;
	}

	public void setAgent_uuid(String AGENT_UUID) {
		this.AGENT_UUID = AGENT_UUID;
	}

	public String getType_uuid() {
		return this.TYPE_UUID;
	}

	public void setType_uuid(String TYPE_UUID) {
		this.TYPE_UUID = TYPE_UUID;
	}

	public String getCommon_name() {
		return this.COMMON_NAME;
	}

	public void setCommon_name(String COMMON_NAME) {
		this.COMMON_NAME = COMMON_NAME;
	}

	public String getDescription() {
		return this.DESCRIPTION;
	}

	public void setDescription(String DESCRIPTION) {
		this.DESCRIPTION = DESCRIPTION;
	}

	public String getSee_also() {
		return this.SEE_ALSO;
	}

	public void setSee_also(String SEE_ALSO) {
		this.SEE_ALSO = SEE_ALSO;
	}

	public String getStreet() {
		return this.STREET;
	}

	public void setStreet(String STREET) {
		this.STREET = STREET;
	}

	public String getSurname() {
		return this.SURNAME;
	}

	public void setSurname(String SURNAME) {
		this.SURNAME = SURNAME;
	}

	public String getTelephone_number() {
		return this.TELEPHONE_NUMBER;
	}

	public void setTelephone_number(String TELEPHONE_NUMBER) {
		this.TELEPHONE_NUMBER = TELEPHONE_NUMBER;
	}

	public String getFax_number() {
		return this.FAX_NUMBER;
	}

	public void setFax_number(String FAX_NUMBER) {
		this.FAX_NUMBER = FAX_NUMBER;
	}

	public String getLocality_name() {
		return this.LOCALITY_NAME;
	}

	public void setLocality_name(String LOCALITY_NAME) {
		this.LOCALITY_NAME = LOCALITY_NAME;
	}

	public String getOu() {
		return this.OU;
	}

	public void setOu(String OU) {
		this.OU = OU;
	}

	public String getPhysical_delivery_office_name() {
		return this.PHYSICAL_DELIVERY_OFFICE_NAME;
	}

	public void setPhysical_delivery_office_name(String PHYSICAL_DELIVERY_OFFICE_NAME) {
		this.PHYSICAL_DELIVERY_OFFICE_NAME = PHYSICAL_DELIVERY_OFFICE_NAME;
	}

	public String getPostal_address() {
		return this.POSTAL_ADDRESS;
	}

	public void setPostal_address(String POSTAL_ADDRESS) {
		this.POSTAL_ADDRESS = POSTAL_ADDRESS;
	}

	public String getPostal_code() {
		return this.POSTAL_CODE;
	}

	public void setPostal_code(String POSTAL_CODE) {
		this.POSTAL_CODE = POSTAL_CODE;
	}

	public String getPost_office_box() {
		return this.POST_OFFICE_BOX;
	}

	public void setPost_office_box(String POST_OFFICE_BOX) {
		this.POST_OFFICE_BOX = POST_OFFICE_BOX;
	}

	public String getState_province_name() {
		return this.STATE_PROVINCE_NAME;
	}

	public void setState_province_name(String STATE_PROVINCE_NAME) {
		this.STATE_PROVINCE_NAME = STATE_PROVINCE_NAME;
	}

	public String getStreet_address() {
		return this.STREET_ADDRESS;
	}

	public void setStreet_address(String STREET_ADDRESS) {
		this.STREET_ADDRESS = STREET_ADDRESS;
	}

	public String getTitle() {
		return this.TITLE;
	}

	public void setTitle(String TITLE) {
		this.TITLE = TITLE;
	}

	public String getBusiness_category() {
		return this.BUSINESS_CATEGORY;
	}

	public void setBusiness_category(String BUSINESS_CATEGORY) {
		this.BUSINESS_CATEGORY = BUSINESS_CATEGORY;
	}

	public String getCar_license() {
		return this.CAR_LICENSE;
	}

	public void setCar_license(String CAR_LICENSE) {
		this.CAR_LICENSE = CAR_LICENSE;
	}

	public String getDepartment_number() {
		return this.DEPARTMENT_NUMBER;
	}

	public void setDepartment_number(String DEPARTMENT_NUMBER) {
		this.DEPARTMENT_NUMBER = DEPARTMENT_NUMBER;
	}

	public String getDisplay_name() {
		return this.DISPLAY_NAME;
	}

	public void setDisplay_name(String DISPLAY_NAME) {
		this.DISPLAY_NAME = DISPLAY_NAME;
	}

	public String getEmployee_number() {
		return this.EMPLOYEE_NUMBER;
	}

	public void setEmployee_number(String EMPLOYEE_NUMBER) {
		this.EMPLOYEE_NUMBER = EMPLOYEE_NUMBER;
	}

	public String getEmployee_type() {
		return this.EMPLOYEE_TYPE;
	}

	public void setEmployee_type(String EMPLOYEE_TYPE) {
		this.EMPLOYEE_TYPE = EMPLOYEE_TYPE;
	}

	public String getGiven_name() {
		return this.GIVEN_NAME;
	}

	public void setGiven_name(String GIVEN_NAME) {
		this.GIVEN_NAME = GIVEN_NAME;
	}

	public String getHome_phone() {
		return this.HOME_PHONE;
	}

	public void setHome_phone(String HOME_PHONE) {
		this.HOME_PHONE = HOME_PHONE;
	}

	public String getHome_postal_address() {
		return this.HOME_POSTAL_ADDRESS;
	}

	public void setHome_postal_address(String HOME_POSTAL_ADDRESS) {
		this.HOME_POSTAL_ADDRESS = HOME_POSTAL_ADDRESS;
	}

	public String getInitials() {
		return this.INITIALS;
	}

	public void setInitials(String INITIALS) {
		this.INITIALS = INITIALS;
	}

	public String getJpeg_photo() {
		return this.JPEG_PHOTO;
	}

	public void setJpeg_photo(String JPEG_PHOTO) {
		this.JPEG_PHOTO = JPEG_PHOTO;
	}

	public String getLabeled_uri() {
		return this.LABELED_URI;
	}

	public void setLabeled_uri(String LABELED_URI) {
		this.LABELED_URI = LABELED_URI;
	}

	public String getMail() {
		return this.MAIL;
	}

	public void setMail(String MAIL) {
		this.MAIL = MAIL;
	}

	public String getManager() {
		return this.MANAGER;
	}

	public void setManager(String MANAGER) {
		this.MANAGER = MANAGER;
	}

	public String getMobile() {
		return this.MOBILE;
	}

	public void setMobile(String MOBILE) {
		this.MOBILE = MOBILE;
	}

	public String getOrganization() {
		return this.ORGANIZATION;
	}

	public void setOrganization(String ORGANIZATION) {
		this.ORGANIZATION = ORGANIZATION;
	}

	public String getPager() {
		return this.PAGER;
	}

	public void setPager(String PAGER) {
		this.PAGER = PAGER;
	}

	public String getPreferred_language() {
		return this.PREFERRED_LANGUAGE;
	}

	public void setPreferred_language(String PREFERRED_LANGUAGE) {
		this.PREFERRED_LANGUAGE = PREFERRED_LANGUAGE;
	}

	public String getRoom_number() {
		return this.ROOM_NUMBER;
	}

	public void setRoom_number(String ROOM_NUMBER) {
		this.ROOM_NUMBER = ROOM_NUMBER;
	}

	public String getSecretary() {
		return this.SECRETARY;
	}

	public void setSecretary(String SECRETARY) {
		this.SECRETARY = SECRETARY;
	}

	public String getUid_c() {
		return this.UID_C;
	}

	public void setUid_c(String UID_C) {
		this.UID_C = UID_C;
	}

	public String getUser_certificate() {
		return this.USER_CERTIFICATE;
	}

	public void setUser_certificate(String USER_CERTIFICATE) {
		this.USER_CERTIFICATE = USER_CERTIFICATE;
	}

	public String getUser_pkcs12() {
		return this.USER_PKCS12;
	}

	public void setUser_pkcs12(String USER_PKCS12) {
		this.USER_PKCS12 = USER_PKCS12;
	}

	public String getUser_smime_certificate() {
		return this.USER_SMIME_CERTIFICATE;
	}

	public void setUser_smime_certificate(String USER_SMIME_CERTIFICATE) {
		this.USER_SMIME_CERTIFICATE = USER_SMIME_CERTIFICATE;
	}

	public String getX500_unique_id() {
		return this.X500_UNIQUE_ID;
	}

	public void setX500_unique_id(String X500_UNIQUE_ID) {
		this.X500_UNIQUE_ID = X500_UNIQUE_ID;
	}

	public String getAffiliation() {
		return this.AFFILIATION;
	}

	public void setAffiliation(String AFFILIATION) {
		this.AFFILIATION = AFFILIATION;
	}

	public String getEntitlement() {
		return this.ENTITLEMENT;
	}

	public void setEntitlement(String ENTITLEMENT) {
		this.ENTITLEMENT = ENTITLEMENT;
	}

	public String getNickname() {
		return this.NICKNAME;
	}

	public void setNickname(String NICKNAME) {
		this.NICKNAME = NICKNAME;
	}

	public String getOrg_dn() {
		return this.ORG_DN;
	}

	public void setOrg_dn(String ORG_DN) {
		this.ORG_DN = ORG_DN;
	}

	public String getOrg_unit_dn() {
		return this.ORG_UNIT_DN;
	}

	public void setOrg_unit_dn(String ORG_UNIT_DN) {
		this.ORG_UNIT_DN = ORG_UNIT_DN;
	}

	public String getPrimary_affiliation() {
		return this.PRIMARY_AFFILIATION;
	}

	public void setPrimary_affiliation(String PRIMARY_AFFILIATION) {
		this.PRIMARY_AFFILIATION = PRIMARY_AFFILIATION;
	}

	public String getPrimary_org_unit_dn() {
		return this.PRIMARY_ORG_UNIT_DN;
	}

	public void setPrimary_org_unit_dn(String PRIMARY_ORG_UNIT_DN) {
		this.PRIMARY_ORG_UNIT_DN = PRIMARY_ORG_UNIT_DN;
	}

	public String getPrincipal_name() {
		return this.PRINCIPAL_NAME;
	}

	public void setPrincipal_name(String PRINCIPAL_NAME) {
		this.PRINCIPAL_NAME = PRINCIPAL_NAME;
	}

	public String getPrincipal_name_prior() {
		return this.PRINCIPAL_NAME_PRIOR;
	}

	public void setPrincipal_name_prior(String PRINCIPAL_NAME_PRIOR) {
		this.PRINCIPAL_NAME_PRIOR = PRINCIPAL_NAME_PRIOR;
	}

	public String getScoped_affiliation() {
		return this.SCOPED_AFFILIATION;
	}

	public void setScoped_affiliation(String SCOPED_AFFILIATION) {
		this.SCOPED_AFFILIATION = SCOPED_AFFILIATION;
	}

	public String getTargeted_id() {
		return this.TARGETED_ID;
	}

	public void setTargeted_id(String TARGETED_ID) {
		this.TARGETED_ID = TARGETED_ID;
	}

	public String getAssurance() {
		return this.ASSURANCE;
	}

	public void setAssurance(String ASSURANCE) {
		this.ASSURANCE = ASSURANCE;
	}

	public String getUnique_id() {
		return this.UNIQUE_ID;
	}

	public void setUnique_id(String UNIQUE_ID) {
		this.UNIQUE_ID = UNIQUE_ID;
	}

	public String getOrcid() {
		return this.ORCID;
	}

	public void setOrcid(String ORCID) {
		this.ORCID = ORCID;
	}

	public String getCampus() {
		return this.CAMPUS;
	}

	public void setCampus(String CAMPUS) {
		this.CAMPUS = CAMPUS;
	}

	public String getHide_private_info() {
		return this.HIDE_PRIVATE_INFO;
	}

	public void setHide_private_info(String HIDE_PRIVATE_INFO) {
		this.HIDE_PRIVATE_INFO = HIDE_PRIVATE_INFO;
	}

	public String getHide_public_info() {
		return this.HIDE_PUBLIC_INFO;
	}

	public void setHide_public_info(String HIDE_PUBLIC_INFO) {
		this.HIDE_PUBLIC_INFO = HIDE_PUBLIC_INFO;
	}

	public String getNotes() {
		return this.NOTES;
	}

	public void setNotes(String NOTES) {
		this.NOTES = NOTES;
	}

	public String getPicture_url() {
		return this.PICTURE_URL;
	}

	public void setPicture_url(String PICTURE_URL) {
		this.PICTURE_URL = PICTURE_URL;
	}

	public String getSystem_picture_preferred() {
		return this.SYSTEM_PICTURE_PREFERRED;
	}

	public void setSystem_picture_preferred(String SYSTEM_PICTURE_PREFERRED) {
		this.SYSTEM_PICTURE_PREFERRED = SYSTEM_PICTURE_PREFERRED;
	}

	public String getFerpaenabled() {
		return this.ferpaEnabled;
	}

	public void setFerpaenabled(String ferpaEnabled) {
		this.ferpaEnabled = ferpaEnabled;
	}

	public Date getDateofbirth() {
		return this.dateOfBirth;
	}

	public void setDateofbirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getFavourite_books() {
		return this.FAVOURITE_BOOKS;
	}

	public void setFavourite_books(String FAVOURITE_BOOKS) {
		this.FAVOURITE_BOOKS = FAVOURITE_BOOKS;
	}

	public String getFavourite_tv_shows() {
		return this.FAVOURITE_TV_SHOWS;
	}

	public void setFavourite_tv_shows(String FAVOURITE_TV_SHOWS) {
		this.FAVOURITE_TV_SHOWS = FAVOURITE_TV_SHOWS;
	}

	public String getFavourite_movies() {
		return this.FAVOURITE_MOVIES;
	}

	public void setFavourite_movies(String FAVOURITE_MOVIES) {
		this.FAVOURITE_MOVIES = FAVOURITE_MOVIES;
	}

	public String getFavourite_quotes() {
		return this.FAVOURITE_QUOTES;
	}

	public void setFavourite_quotes(String FAVOURITE_QUOTES) {
		this.FAVOURITE_QUOTES = FAVOURITE_QUOTES;
	}

	public String getEducation_course() {
		return this.EDUCATION_COURSE;
	}

	public void setEducation_course(String EDUCATION_COURSE) {
		this.EDUCATION_COURSE = EDUCATION_COURSE;
	}

	public String getEducation_subjects() {
		return this.EDUCATION_SUBJECTS;
	}

	public void setEducation_subjects(String EDUCATION_SUBJECTS) {
		this.EDUCATION_SUBJECTS = EDUCATION_SUBJECTS;
	}

	public String getNormalizedmobile() {
		return this.NORMALIZEDMOBILE;
	}

	public void setNormalizedmobile(String NORMALIZEDMOBILE) {
		this.NORMALIZEDMOBILE = NORMALIZEDMOBILE;
	}

	public String getStaff_profile() {
		return this.STAFF_PROFILE;
	}

	public void setStaff_profile(String STAFF_PROFILE) {
		this.STAFF_PROFILE = STAFF_PROFILE;
	}

	public String getUniversity_profile_url() {
		return this.UNIVERSITY_PROFILE_URL;
	}

	public void setUniversity_profile_url(String UNIVERSITY_PROFILE_URL) {
		this.UNIVERSITY_PROFILE_URL = UNIVERSITY_PROFILE_URL;
	}

	public String getAcademic_profile_url() {
		return this.ACADEMIC_PROFILE_URL;
	}

	public void setAcademic_profile_url(String ACADEMIC_PROFILE_URL) {
		this.ACADEMIC_PROFILE_URL = ACADEMIC_PROFILE_URL;
	}

	public String getPublications() {
		return this.PUBLICATIONS;
	}

	public void setPublications(String PUBLICATIONS) {
		this.PUBLICATIONS = PUBLICATIONS;
	}

	public String getBusiness_biography() {
		return this.BUSINESS_BIOGRAPHY;
	}

	public void setBusiness_biography(String BUSINESS_BIOGRAPHY) {
		this.BUSINESS_BIOGRAPHY = BUSINESS_BIOGRAPHY;
	}

	public String getPhonetic_pronunciation() {
		return this.PHONETIC_PRONUNCIATION;
	}

	public void setPhonetic_pronunciation(String PHONETIC_PRONUNCIATION) {
		this.PHONETIC_PRONUNCIATION = PHONETIC_PRONUNCIATION;
	}

	public String getPronouns() {
		return this.PRONOUNS;
	}

	public void setPronouns(String PRONOUNS) {
		this.PRONOUNS = PRONOUNS;
	}

}
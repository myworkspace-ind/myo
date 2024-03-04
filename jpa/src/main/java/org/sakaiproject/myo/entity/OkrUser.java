package org.sakaiproject.myo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the okr_users database table.
 * 
 */
@Entity
@Table(name="okr_users")
@NamedQuery(name="OkrUser.findAll", query="SELECT o FROM OkrUser o")
public class OkrUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private UUID userId;

	private String department;

	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	@Column(name="manager_email")
	private String managerEmail;

	private String name;

	private Integer numobjs;

	private String role;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	//bi-directional many-to-one association to OkrDelegateUser
	@OneToMany(mappedBy="okrUser")
	private List<OkrDelegateUser> okrDelegateUsers;

	//bi-directional many-to-one association to OkrKr
	@OneToMany(mappedBy="okrUser1")
	private List<OkrKr> okrKrs1;

	//bi-directional many-to-one association to OkrKr
	@OneToMany(mappedBy="okrUser2")
	private List<OkrKr> okrKrs2;

	//bi-directional many-to-one association to OkrObj
	@OneToMany(mappedBy="okrUser1")
	private List<OkrObj> okrObjs1;

	//bi-directional many-to-one association to OkrObj
	@OneToMany(mappedBy="okrUser2")
	private List<OkrObj> okrObjs2;

	//bi-directional many-to-one association to OkrObj
	@OneToMany(mappedBy="okrUser3")
	private List<OkrObj> okrObjs3;

	//bi-directional many-to-one association to OkrOrg
	@OneToMany(mappedBy="okrUser")
	private List<OkrOrg> okrOrgs;

	//bi-directional many-to-one association to OkrOrg
	@ManyToOne
	@JoinColumn(name="org_id")
	private OkrOrg okrOrg;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="manager_id")
	private OkrUser okrUser;

	//bi-directional many-to-one association to OkrUser
	@OneToMany(mappedBy="okrUser")
	private List<OkrUser> okrUsers;

	public OkrUser() {
	}

	public UUID getUserId() {
		return this.userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getManagerEmail() {
		return this.managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumobjs() {
		return this.numobjs;
	}

	public void setNumobjs(Integer numobjs) {
		this.numobjs = numobjs;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<OkrDelegateUser> getOkrDelegateUsers() {
		return this.okrDelegateUsers;
	}

	public void setOkrDelegateUsers(List<OkrDelegateUser> okrDelegateUsers) {
		this.okrDelegateUsers = okrDelegateUsers;
	}

	public OkrDelegateUser addOkrDelegateUser(OkrDelegateUser okrDelegateUser) {
		getOkrDelegateUsers().add(okrDelegateUser);
		okrDelegateUser.setOkrUser(this);

		return okrDelegateUser;
	}

	public OkrDelegateUser removeOkrDelegateUser(OkrDelegateUser okrDelegateUser) {
		getOkrDelegateUsers().remove(okrDelegateUser);
		okrDelegateUser.setOkrUser(null);

		return okrDelegateUser;
	}

	public List<OkrKr> getOkrKrs1() {
		return this.okrKrs1;
	}

	public void setOkrKrs1(List<OkrKr> okrKrs1) {
		this.okrKrs1 = okrKrs1;
	}

	public OkrKr addOkrKrs1(OkrKr okrKrs1) {
		getOkrKrs1().add(okrKrs1);
		okrKrs1.setOkrUser1(this);

		return okrKrs1;
	}

	public OkrKr removeOkrKrs1(OkrKr okrKrs1) {
		getOkrKrs1().remove(okrKrs1);
		okrKrs1.setOkrUser1(null);

		return okrKrs1;
	}

	public List<OkrKr> getOkrKrs2() {
		return this.okrKrs2;
	}

	public void setOkrKrs2(List<OkrKr> okrKrs2) {
		this.okrKrs2 = okrKrs2;
	}

	public OkrKr addOkrKrs2(OkrKr okrKrs2) {
		getOkrKrs2().add(okrKrs2);
		okrKrs2.setOkrUser2(this);

		return okrKrs2;
	}

	public OkrKr removeOkrKrs2(OkrKr okrKrs2) {
		getOkrKrs2().remove(okrKrs2);
		okrKrs2.setOkrUser2(null);

		return okrKrs2;
	}

	public List<OkrObj> getOkrObjs1() {
		return this.okrObjs1;
	}

	public void setOkrObjs1(List<OkrObj> okrObjs1) {
		this.okrObjs1 = okrObjs1;
	}

	public OkrObj addOkrObjs1(OkrObj okrObjs1) {
		getOkrObjs1().add(okrObjs1);
		okrObjs1.setOkrUser1(this);

		return okrObjs1;
	}

	public OkrObj removeOkrObjs1(OkrObj okrObjs1) {
		getOkrObjs1().remove(okrObjs1);
		okrObjs1.setOkrUser1(null);

		return okrObjs1;
	}

	public List<OkrObj> getOkrObjs2() {
		return this.okrObjs2;
	}

	public void setOkrObjs2(List<OkrObj> okrObjs2) {
		this.okrObjs2 = okrObjs2;
	}

	public OkrObj addOkrObjs2(OkrObj okrObjs2) {
		getOkrObjs2().add(okrObjs2);
		okrObjs2.setOkrUser2(this);

		return okrObjs2;
	}

	public OkrObj removeOkrObjs2(OkrObj okrObjs2) {
		getOkrObjs2().remove(okrObjs2);
		okrObjs2.setOkrUser2(null);

		return okrObjs2;
	}

	public List<OkrObj> getOkrObjs3() {
		return this.okrObjs3;
	}

	public void setOkrObjs3(List<OkrObj> okrObjs3) {
		this.okrObjs3 = okrObjs3;
	}

	public OkrObj addOkrObjs3(OkrObj okrObjs3) {
		getOkrObjs3().add(okrObjs3);
		okrObjs3.setOkrUser3(this);

		return okrObjs3;
	}

	public OkrObj removeOkrObjs3(OkrObj okrObjs3) {
		getOkrObjs3().remove(okrObjs3);
		okrObjs3.setOkrUser3(null);

		return okrObjs3;
	}

	public List<OkrOrg> getOkrOrgs() {
		return this.okrOrgs;
	}

	public void setOkrOrgs(List<OkrOrg> okrOrgs) {
		this.okrOrgs = okrOrgs;
	}

	public OkrOrg addOkrOrg(OkrOrg okrOrg) {
		getOkrOrgs().add(okrOrg);
		okrOrg.setOkrUser(this);

		return okrOrg;
	}

	public OkrOrg removeOkrOrg(OkrOrg okrOrg) {
		getOkrOrgs().remove(okrOrg);
		okrOrg.setOkrUser(null);

		return okrOrg;
	}

	public OkrOrg getOkrOrg() {
		return this.okrOrg;
	}

	public void setOkrOrg(OkrOrg okrOrg) {
		this.okrOrg = okrOrg;
	}

	public OkrUser getOkrUser() {
		return this.okrUser;
	}

	public void setOkrUser(OkrUser okrUser) {
		this.okrUser = okrUser;
	}

	public List<OkrUser> getOkrUsers() {
		return this.okrUsers;
	}

	public void setOkrUsers(List<OkrUser> okrUsers) {
		this.okrUsers = okrUsers;
	}

	public OkrUser addOkrUser(OkrUser okrUser) {
		getOkrUsers().add(okrUser);
		okrUser.setOkrUser(this);

		return okrUser;
	}

	public OkrUser removeOkrUser(OkrUser okrUser) {
		getOkrUsers().remove(okrUser);
		okrUser.setOkrUser(null);

		return okrUser;
	}

}
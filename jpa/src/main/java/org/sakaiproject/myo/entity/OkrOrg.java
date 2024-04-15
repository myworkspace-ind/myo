package org.sakaiproject.myo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the okr_orgs database table.
 * 
 */
@Entity
@Table(name="okr_orgs")
@NamedQuery(name="OkrOrg.findAll", query="SELECT o FROM OkrOrg o")
public class OkrOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@Column(name="imp_name")
	private String impName;

	private String name;

	//bi-directional many-to-one association to OkrObj
	@OneToMany(mappedBy="okrOrg")
	private List<OkrObj> okrObjs;

	//bi-directional many-to-one association to OkrOrg
	@ManyToOne
	@JoinColumn(name="org_id")
	private OkrOrg okrOrg;

	//bi-directional many-to-one association to OkrOrg
	@OneToMany(mappedBy="okrOrg")
	private List<OkrOrg> okrOrgs;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="manager_id")
	private OkrUser okrUser;

	//bi-directional many-to-one association to OkrUser
	@OneToMany(mappedBy="okrOrg")
	private List<OkrUser> okrUsers;

	public OkrOrg() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getImpName() {
		return this.impName;
	}

	public void setImpName(String impName) {
		this.impName = impName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OkrObj> getOkrObjs() {
		return this.okrObjs;
	}

	public void setOkrObjs(List<OkrObj> okrObjs) {
		this.okrObjs = okrObjs;
	}

	public OkrObj addOkrObj(OkrObj okrObj) {
		getOkrObjs().add(okrObj);
		okrObj.setOkrOrg(this);

		return okrObj;
	}

	public OkrObj removeOkrObj(OkrObj okrObj) {
		getOkrObjs().remove(okrObj);
		okrObj.setOkrOrg(null);

		return okrObj;
	}

	public OkrOrg getOkrOrg() {
		return this.okrOrg;
	}

	public void setOkrOrg(OkrOrg okrOrg) {
		this.okrOrg = okrOrg;
	}

	public List<OkrOrg> getOkrOrgs() {
		return this.okrOrgs;
	}

	public void setOkrOrgs(List<OkrOrg> okrOrgs) {
		this.okrOrgs = okrOrgs;
	}

	public OkrOrg addOkrOrg(OkrOrg okrOrg) {
		getOkrOrgs().add(okrOrg);
		okrOrg.setOkrOrg(this);

		return okrOrg;
	}

	public OkrOrg removeOkrOrg(OkrOrg okrOrg) {
		getOkrOrgs().remove(okrOrg);
		okrOrg.setOkrOrg(null);

		return okrOrg;
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
		okrUser.setOkrOrg(this);

		return okrUser;
	}

	public OkrUser removeOkrUser(OkrUser okrUser) {
		getOkrUsers().remove(okrUser);
		okrUser.setOkrOrg(null);

		return okrUser;
	}

}
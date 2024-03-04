package org.sakaiproject.myo.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the okr_delegate_users database table.
 * 
 */
@Entity
@Table(name="okr_delegate_users")
@NamedQuery(name="OkrDelegateUser.findAll", query="SELECT o FROM OkrDelegateUser o")
public class OkrDelegateUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OkrDelegateUserPK id;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	private OkrUser okrUser;

	public OkrDelegateUser() {
	}

	public OkrDelegateUserPK getId() {
		return this.id;
	}

	public void setId(OkrDelegateUserPK id) {
		this.id = id;
	}

	public OkrUser getOkrUser() {
		return this.okrUser;
	}

	public void setOkrUser(OkrUser okrUser) {
		this.okrUser = okrUser;
	}

}
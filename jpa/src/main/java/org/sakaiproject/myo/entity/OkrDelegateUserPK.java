package org.sakaiproject.myo.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

/**
 * The primary key class for the okr_delegate_users database table.
 * 
 */
@Embeddable
public class OkrDelegateUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false)
	private UUID userId;

	@Column(name="org_id")
	private UUID orgId;

	public OkrDelegateUserPK() {
	}
	public UUID getUserId() {
		return this.userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public UUID getOrgId() {
		return this.orgId;
	}
	public void setOrgId(UUID orgId) {
		this.orgId = orgId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OkrDelegateUserPK)) {
			return false;
		}
		OkrDelegateUserPK castOther = (OkrDelegateUserPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.orgId.equals(castOther.orgId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.orgId.hashCode();
		
		return hash;
	}
}
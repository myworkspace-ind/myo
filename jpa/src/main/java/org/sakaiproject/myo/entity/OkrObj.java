package org.sakaiproject.myo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the okr_objs database table.
 * 
 */
@Entity
@Table(name="okr_objs")
@NamedQuery(name="OkrObj.findAll", query="SELECT o FROM OkrObj o")
public class OkrObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	private String comment;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="custom_progress")
	private Integer customProgress;

	@Column(name="is_custom_progress")
	private Integer isCustomProgress;

	@Temporal(TemporalType.DATE)
	@Column(name="last_modified")
	private Date lastModified;

	private String name;

	@Column(name="okr_id")
	private UUID okrId;

	@Temporal(TemporalType.DATE)
	@Column(name="review_date")
	private Date reviewDate;

	@Column(name="src_obj_id")
	private UUID srcObjId;

	private String status;

	private Long weight;

	//bi-directional many-to-one association to OkrKr
	@OneToMany(mappedBy="okrObj")
	private List<OkrKr> okrKrs;

	//bi-directional many-to-one association to OkrObj
	@ManyToOne
	@JoinColumn(name="linked_to_id")
	private OkrObj okrObj;

	//bi-directional many-to-one association to OkrObj
	@OneToMany(mappedBy="okrObj")
	private List<OkrObj> okrObjs;

	//bi-directional many-to-one association to OkrOrg
	@ManyToOne
	@JoinColumn(name="org_id")
	private OkrOrg okrOrg;

	//bi-directional many-to-one association to OkrPeriod
	@ManyToOne
	@JoinColumn(name="period_id")
	private OkrPeriod okrPeriod;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="create_by")
	private OkrUser okrUser1;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="last_modified_by")
	private OkrUser okrUser2;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private OkrUser okrUser3;

	public OkrObj() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getCustomProgress() {
		return this.customProgress;
	}

	public void setCustomProgress(Integer customProgress) {
		this.customProgress = customProgress;
	}

	public Integer getIsCustomProgress() {
		return this.isCustomProgress;
	}

	public void setIsCustomProgress(Integer isCustomProgress) {
		this.isCustomProgress = isCustomProgress;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getOkrId() {
		return this.okrId;
	}

	public void setOkrId(UUID okrId) {
		this.okrId = okrId;
	}

	public Date getReviewDate() {
		return this.reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public UUID getSrcObjId() {
		return this.srcObjId;
	}

	public void setSrcObjId(UUID srcObjId) {
		this.srcObjId = srcObjId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getWeight() {
		return this.weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public List<OkrKr> getOkrKrs() {
		return this.okrKrs;
	}

	public void setOkrKrs(List<OkrKr> okrKrs) {
		this.okrKrs = okrKrs;
	}

	public OkrKr addOkrKr(OkrKr okrKr) {
		getOkrKrs().add(okrKr);
		okrKr.setOkrObj(this);

		return okrKr;
	}

	public OkrKr removeOkrKr(OkrKr okrKr) {
		getOkrKrs().remove(okrKr);
		okrKr.setOkrObj(null);

		return okrKr;
	}

	public OkrObj getOkrObj() {
		return this.okrObj;
	}

	public void setOkrObj(OkrObj okrObj) {
		this.okrObj = okrObj;
	}

	public List<OkrObj> getOkrObjs() {
		return this.okrObjs;
	}

	public void setOkrObjs(List<OkrObj> okrObjs) {
		this.okrObjs = okrObjs;
	}

	public OkrObj addOkrObj(OkrObj okrObj) {
		getOkrObjs().add(okrObj);
		okrObj.setOkrObj(this);

		return okrObj;
	}

	public OkrObj removeOkrObj(OkrObj okrObj) {
		getOkrObjs().remove(okrObj);
		okrObj.setOkrObj(null);

		return okrObj;
	}

	public OkrOrg getOkrOrg() {
		return this.okrOrg;
	}

	public void setOkrOrg(OkrOrg okrOrg) {
		this.okrOrg = okrOrg;
	}

	public OkrPeriod getOkrPeriod() {
		return this.okrPeriod;
	}

	public void setOkrPeriod(OkrPeriod okrPeriod) {
		this.okrPeriod = okrPeriod;
	}

	public OkrUser getOkrUser1() {
		return this.okrUser1;
	}

	public void setOkrUser1(OkrUser okrUser1) {
		this.okrUser1 = okrUser1;
	}

	public OkrUser getOkrUser2() {
		return this.okrUser2;
	}

	public void setOkrUser2(OkrUser okrUser2) {
		this.okrUser2 = okrUser2;
	}

	public OkrUser getOkrUser3() {
		return this.okrUser3;
	}

	public void setOkrUser3(OkrUser okrUser3) {
		this.okrUser3 = okrUser3;
	}

}
package org.sakaiproject.myo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the okr_krs database table.
 * 
 */
@Entity
@Table(name="okr_krs")
@NamedQuery(name="OkrKr.findAll", query="SELECT o FROM OkrKr o")
public class OkrKr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@Column(name="comment_link")
	private String commentLink;

	@Temporal(TemporalType.DATE)
	@Column(name="completed_date")
	private Date completedDate;

	@Column(name="created_at")
	private Timestamp createdAt;

	private Integer criterias;

	@Temporal(TemporalType.DATE)
	private Date duedate;

	private float grade;

	private String itype;

	@Column(name="kr_weight")
	private Long krWeight;

	@Temporal(TemporalType.DATE)
	@Column(name="last_modified")
	private Date lastModified;

	@Column(name="linked_by")
	private UUID linkedBy;

	@Column(name="linked_to_id")
	private UUID linkedToId;

	@Temporal(TemporalType.DATE)
	private Date milestone;

	private String name;

	private String note;

	private float progress;

	@Temporal(TemporalType.DATE)
	@Column(name="progress_milestone")
	private Date progressMilestone;

	@Column(name="self_grade")
	private Integer selfGrade;

	private float target;

	//bi-directional many-to-one association to OkrKr
	@ManyToOne
	@JoinColumn(name="linked_to_kr")
	private OkrKr okrKr;

	//bi-directional many-to-one association to OkrKr
	@OneToMany(mappedBy="okrKr")
	private List<OkrKr> okrKrs;

	//bi-directional many-to-one association to OkrObj
	@ManyToOne
	@JoinColumn(name="obj_id")
	private OkrObj okrObj;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="create_by")
	private OkrUser okrUser1;

	//bi-directional many-to-one association to OkrUser
	@ManyToOne
	@JoinColumn(name="last_modified_by")
	private OkrUser okrUser2;

	public OkrKr() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCommentLink() {
		return this.commentLink;
	}

	public void setCommentLink(String commentLink) {
		this.commentLink = commentLink;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getCriterias() {
		return this.criterias;
	}

	public void setCriterias(Integer criterias) {
		this.criterias = criterias;
	}

	public Date getDuedate() {
		return this.duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public float getGrade() {
		return this.grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getItype() {
		return this.itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public Long getKrWeight() {
		return this.krWeight;
	}

	public void setKrWeight(Long krWeight) {
		this.krWeight = krWeight;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Object getLinkedBy() {
		return this.linkedBy;
	}

	public void setLinkedBy(UUID linkedBy) {
		this.linkedBy = linkedBy;
	}

	public Object getLinkedToId() {
		return this.linkedToId;
	}

	public void setLinkedToId(UUID linkedToId) {
		this.linkedToId = linkedToId;
	}

	public Date getMilestone() {
		return this.milestone;
	}

	public void setMilestone(Date milestone) {
		this.milestone = milestone;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getProgress() {
		return this.progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public Date getProgressMilestone() {
		return this.progressMilestone;
	}

	public void setProgressMilestone(Date progressMilestone) {
		this.progressMilestone = progressMilestone;
	}

	public Integer getSelfGrade() {
		return this.selfGrade;
	}

	public void setSelfGrade(Integer selfGrade) {
		this.selfGrade = selfGrade;
	}

	public float getTarget() {
		return this.target;
	}

	public void setTarget(float target) {
		this.target = target;
	}

	public OkrKr getOkrKr() {
		return this.okrKr;
	}

	public void setOkrKr(OkrKr okrKr) {
		this.okrKr = okrKr;
	}

	public List<OkrKr> getOkrKrs() {
		return this.okrKrs;
	}

	public void setOkrKrs(List<OkrKr> okrKrs) {
		this.okrKrs = okrKrs;
	}

	public OkrKr addOkrKr(OkrKr okrKr) {
		getOkrKrs().add(okrKr);
		okrKr.setOkrKr(this);

		return okrKr;
	}

	public OkrKr removeOkrKr(OkrKr okrKr) {
		getOkrKrs().remove(okrKr);
		okrKr.setOkrKr(null);

		return okrKr;
	}

	public OkrObj getOkrObj() {
		return this.okrObj;
	}

	public void setOkrObj(OkrObj okrObj) {
		this.okrObj = okrObj;
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

}
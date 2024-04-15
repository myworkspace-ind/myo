package org.sakaiproject.myo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the okr_periods database table.
 * 
 */
@Entity
@Table(name="okr_periods")
@NamedQuery(name="OkrPeriod.findAll", query="SELECT o FROM OkrPeriod o")
public class OkrPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	//bi-directional many-to-one association to OkrObj
	@OneToMany(mappedBy="okrPeriod")
	private List<OkrObj> okrObjs;

	public OkrPeriod() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<OkrObj> getOkrObjs() {
		return this.okrObjs;
	}

	public void setOkrObjs(List<OkrObj> okrObjs) {
		this.okrObjs = okrObjs;
	}

	public OkrObj addOkrObj(OkrObj okrObj) {
		getOkrObjs().add(okrObj);
		okrObj.setOkrPeriod(this);

		return okrObj;
	}

	public OkrObj removeOkrObj(OkrObj okrObj) {
		getOkrObjs().remove(okrObj);
		okrObj.setOkrPeriod(null);

		return okrObj;
	}

}
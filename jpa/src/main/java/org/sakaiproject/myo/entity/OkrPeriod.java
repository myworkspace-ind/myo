package org.sakaiproject.myo.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the okr_periods database table.
 * 
 */
@Entity
//@Table(name="okr_periods")
//@NamedQuery(name="OkrPeriod.findAll", query="SELECT o FROM OkrPeriod o")
@org.hibernate.annotations.NamedQuery(name = "OkrPeriod.findAll", 
query = "from OkrPeriod")
@Getter @Setter
public class OkrPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	private String name;
	private String note;
	private int year;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	//bi-directional many-to-one association to OkrObj
	@OneToMany(mappedBy="okrPeriod")
	private List<OkrObj> okrObjs;

	public OkrPeriod() {
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
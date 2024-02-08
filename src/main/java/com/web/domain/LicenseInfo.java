package com.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name="licenseinfo")
public class LicenseInfo {

	
	@Id
	private String jmNm;
	
	@Column( name = "career", length = 1000)
	private String Career;
	private String implNm;
	private String instiNm;
	@Column( name = "job", length = 1000)
	private String job;
	private String mdobligFIdNm;
	private String seriesNm;
	@Column( name = "summary", length = 1000)
	private String summary;
	@Column( name = "trend", length = 1000)
	private String trend;
	
	public LicenseInfo() {	
    }

	public LicenseInfo(String jmNm, String career, String implNm, String instiNm, String job, String mdobligFIdNm,
			String seriesNm, String summary, String trend) {
		this.jmNm = jmNm;
		this.Career = career;
		this.implNm = implNm;
		this.instiNm = instiNm;
		this.job = job;
		this.mdobligFIdNm = mdobligFIdNm;
		this.seriesNm = seriesNm;
		this.summary = summary;
		this.trend = trend;
	}
	
	
}

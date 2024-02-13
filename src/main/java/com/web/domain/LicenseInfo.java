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
	private String jmnm;
	
	@Column( name = "career", length = 1000)
	private String career;
	private String implnm;
	private String instinm;
	@Column( name = "job", length = 1000)
	private String job;
	private String seriesnm;
	@Column( name = "summary", length = 1000)
	private String summary;
	@Column( name = "trend", length = 1000)
	private String trend;
	
	public LicenseInfo() {	
    }

	public LicenseInfo(String jmnm, String career, String implnm, String instinm, String job,
			String seriesnm, String summary, String trend) {
		this.jmnm = jmnm;
		this.career = career;
		this.implnm = implnm;
		this.instinm = instinm;
		this.job = job;
		this.seriesnm = seriesnm;
		this.summary = summary;
		this.trend = trend;
	}
	
	
}

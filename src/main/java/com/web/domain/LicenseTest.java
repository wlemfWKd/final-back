package com.web.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name="licensetest")
public class LicenseTest {

	@Id
	private String jmfldnm;
	
	private String contents1;
	private String contents2;
	private String contents3;
	
	public LicenseTest() {
	}

	public LicenseTest(String jmfldnm, String contents1, String contents2, String contents3) {
		super();
		this.jmfldnm = jmfldnm;
		this.contents1 = contents1;
		this.contents2 = contents2;
		this.contents3 = contents3;
	}
	
	
}

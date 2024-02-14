package com.web.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name="licenseaccess")
public class LicenseAccess {
	
	@Id
	private String emqualdispnm;
	
	private String grdnm;
	private String grdcd;
	
	public LicenseAccess() {
	}

	public LicenseAccess(String emqualdispnm, String grdnm, String grdcd) {
		this.emqualdispnm = emqualdispnm;
		this.grdnm = grdnm;
		this.grdcd = grdcd;
	}
	
	
	
	
	
	
}

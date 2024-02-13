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
@Table(name="licensrank")
public class LicensRank {

	@Id
	private int examrecptcnt;
	
	private String jmcd;
	private String jmfldnm;
	private String sumyy;
	private int examrecptrank;
	private String grdnm;
	
	public LicensRank() {
	}

	public LicensRank(int examrecptrank, String jmcd, String jmfldnm, String sumyy, int examrecptcnt, String grdnm) {
		this.examrecptrank = examrecptrank;
		this.jmcd = jmcd;
		this.jmfldnm = jmfldnm;
		this.sumyy = sumyy;
		this.examrecptcnt = examrecptcnt;
		this.grdnm = grdnm;
	}
	
	
}

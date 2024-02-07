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
@Builder
@Entity
@Table(name="licenselist")
public class LicenseList {

	
	@Id
	private String jmcd;
	
    private String jmfldnm;
    private String mdobligfldcd;
    private String mdobligfldnm;
    private String obligfldcd;
    private String obligfldnm;
    private String qualgbcd;
    private String qualgbnm;
    private String seriescd;
    private String seriesnm;
    
    
    public LicenseList() {	
    }


	public LicenseList(String jmcd, String jmfldnm, String mdobligfldcd, String mdobligfldnm, String obligfldcd,
			String obligfldnm, String qualgbcd, String qualgbnm, String seriescd, String seriesnm) {
		this.jmcd = jmcd;
		this.jmfldnm = jmfldnm;
		this.mdobligfldcd = mdobligfldcd;
		this.mdobligfldnm = mdobligfldnm;
		this.obligfldcd = obligfldcd;
		this.obligfldnm = obligfldnm;
		this.qualgbcd = qualgbcd;
		this.qualgbnm = qualgbnm;
		this.seriescd = seriescd;
		this.seriesnm = seriesnm;
	}
    
	@Override
    public String toString() {
        return "LicenseList{" +
                "jmcd='" + jmcd + '\'' +
                ", jmfldnm='" + jmfldnm + '\'' +
                ", mdobligfldcd='" + mdobligfldcd + '\'' +
                ", mdobligfldnm='" + mdobligfldnm + '\'' +
                ", obligfldcd='" + obligfldcd + '\'' +
                ", obligfldnm='" + obligfldnm + '\'' +
                ", qualgbcd='" + qualgbcd + '\'' +
                ", qualgbnm='" + qualgbnm + '\'' +
                ", seriescd='" + seriescd + '\'' +
                ", seriesnm='" + seriesnm + '\'' +
                '}';
    }
    
}



//@Id
//private Long jmcd;
//
//private String jmfldnm;
//private Long mdobligfldcd;
//private String mdobligfldnm;
//private Long obligfldcd;
//private String obligfldnm;
//private String qualgbcd;
//private String qualgbnm;
//private Long seriescd;
//private String seriesnm;
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
@ToString
@Table(name="licensedate")
public class LicenseDate {

    @Id
    private String description;
    
    private String docregstartdt;
    private String docregenddt;
    private String docexamdt;
    private String docpassdt;
    private String docsubmitstartdt;
    private String docsubmitentdt;
    private String pracregstartdt;
    private String pracregenddt;
    private String pracexamstartdt;
    private String pracexamenddt;
    private String pracpassdt;

    public LicenseDate() {
    }

    public LicenseDate(String description, String docregstartdt, String docregenddt, String docexamdt, String docpassdt,
            String docsubmitstartdt, String docsubmitentdt, String pracregstartdt, String pracregenddt,
            String pracexamstartdt, String pracexamenddt, String pracpassdt) {
        this.description = description;
        this.docregstartdt = docregstartdt;
        this.docregenddt = docregenddt;
        this.docexamdt = docexamdt;
        this.docpassdt = docpassdt;
        this.docsubmitstartdt = docsubmitstartdt;
        this.docsubmitentdt = docsubmitentdt;
        this.pracregstartdt = pracregstartdt;
        this.pracregenddt = pracregenddt;
        this.pracexamstartdt = pracexamstartdt;
        this.pracexamenddt = pracexamenddt;
        this.pracpassdt = pracpassdt;
    }   
}

package com.web.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "workbook")
public class WbDTO {
	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private int id;
	
	@Id
	private String text;
	private String href;

}

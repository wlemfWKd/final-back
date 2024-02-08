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
@ToString
@Builder
@Entity
@Table(name = "KYOBOCRAWL")
public class KyoboBookCrawl {
	
	@Id
	private String bookName;
	
	private int bookPrice;
	private String viewDetail;
	private String imageName;
	
	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'KYOBO'")
	private String defaultColumn;
	
	public KyoboBookCrawl() {
		
	}
	
	public KyoboBookCrawl(String bookName, int bookPrice, String viewDetail, String imageName, String defaultColumn) {
		super();
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.viewDetail = viewDetail;
		this.imageName = imageName;
		this.defaultColumn = "KYOBO"; // 기본값 설정
	}
	
	

}

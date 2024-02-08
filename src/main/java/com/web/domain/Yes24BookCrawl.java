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
@Table(name = "YES24CRAWL")
public class Yes24BookCrawl {                                                                            

	@Id
	private String bookName;

	private int bookPrice;
	private String viewDetail; //상세보기 링
	private String imageName;
	
	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'YES24'")
	private String defaultColumn;

	// 기본 생성자 추가
	public Yes24BookCrawl() {
	}

	public Yes24BookCrawl(String bookName, int bookPrice, String viewDetail, String imageName, String defaultColumn) {
		super();
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.viewDetail = viewDetail;
		this.imageName = imageName;
		this.defaultColumn = "YES24"; // 기본값 설정
	}
	}

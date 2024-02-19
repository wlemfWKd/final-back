package com.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

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
public class Yes24BookCrawl implements TotalBookCrawl {                                                                            

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yes24_seq_generator")
    @SequenceGenerator(name = "yes24_seq_generator", sequenceName = "yes24_sequence", allocationSize = 1)
    private Long id;
	
	@Column(name = "book_name", nullable =true)
	private String bookName;

	private String bookPrice;
	private String viewDetail; //상세보기 링
	private String imageName;
	
	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'YES24'")
	private String defaultColumn;

	// 기본 생성자 추가
	public Yes24BookCrawl() {
	}

	public Yes24BookCrawl(Long id, String bookName, String bookPrice, String viewDetail, String imageName, String defaultColumn) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.viewDetail = viewDetail;
		this.imageName = imageName;
		this.defaultColumn = "YES24"; // 기본값 설정
	}
	}

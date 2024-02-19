package com.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
public class KyoboBookCrawl implements TotalBookCrawl {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kyobo_seq_generator")
    @SequenceGenerator(name = "kyobo_seq_generator", sequenceName = "kyobo_sequence", allocationSize = 1)
    private Long id;
	
	@Column(name = "book_name", nullable =true)
	private String bookName;
	
	@Column(name = "book_price", nullable =true)
	private String bookPrice;
	private String viewDetail; //상세보기 링
	private String imageName;
	
	
	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'KYOBO'")
	private String defaultColumn;
	
	public KyoboBookCrawl() {
		
	}
	
	public KyoboBookCrawl(Long id, String bookName, String bookPrice, String viewDetail, String imageName, String defaultColumn) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.viewDetail = viewDetail;
		this.imageName = imageName;
		this.defaultColumn = "KYOBO"; // 기본값 설정
	}
	
	

}

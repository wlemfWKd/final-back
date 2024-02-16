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
@Table(name = "ALADINCRAWL")
public class AladinBookCrawl {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aladin_seq_generator")
    @SequenceGenerator(name = "aladin_seq_generator", sequenceName = "aladin_sequence", allocationSize = 1)
    private Long id;
	
	@Column(name = "book_name", nullable =true)
	private String bookName;
	
	@Column(name = "book_price", nullable =true)
	private String bookPrice;
	private String viewDetail; //상세보기 링
	private String imageName;
	
	
	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'ALADIN'")
	private String defaultColumn;
	
	public AladinBookCrawl() {
		
	}
	
	public static class Builder {
        private String bookName;
        private String bookPrice;
        private String imageName;

        public Builder title(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder price(String bookPrice) {
            this.bookPrice = bookPrice;
            return this;
        }

        public Builder imageUrl(String imageName) {
            this.imageName = imageName;
            return this;
        }

       
    }

	public AladinBookCrawl(Long id, String bookName, String bookPrice, String viewDetail, String imageName,
			String defaultColumn) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.viewDetail = viewDetail;
		this.imageName = imageName;
		this.defaultColumn = "ALADIN";
	}
	
	

}

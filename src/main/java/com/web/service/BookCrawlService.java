package com.web.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.Yes24BookCrawl;
import com.web.persistence.CrawlRepository;

@Service
public class BookCrawlService {
	
	@Autowired
	private CrawlRepository crawlRepository;
	
	public BookCrawlService(CrawlRepository crawlRepository) {
        this.crawlRepository = crawlRepository;
    }

    public void saveYes24BookCrawl(Yes24BookCrawl bookCrawl) {
        crawlRepository.save(bookCrawl);
    }

    public void saveYes24BookCrawls(List<Yes24BookCrawl> bookCrawls) {
        crawlRepository.saveAll(bookCrawls);
    }
	
	
	public String generateSearchUrl() {
        try {
        	String searchQuery = "컴퓨터활용";
        	
        	//URL에 한글을 입력받기 위헤 인코더 입력
            String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8.toString());
            
            return "https://www.yes24.com/Product/Search?domain=ALL&query=" + encodedQuery;           
            
        } catch (UnsupportedEncodingException e) {
            // 예외 처리: 인코딩에 실패한 경우
            e.printStackTrace();
            return null; // 또는 예외를 다시 던질 수 있음
        }
    }
	
	@PostConstruct
	public void getBooktDates() throws IOException {
		
		String Book_Data_URL = generateSearchUrl();	//인코딩한 URL String 변수에 담기
		
		Document doc = Jsoup.connect(Book_Data_URL).get();	//URL에 담긴 정보 전부 불러오기
		
		//System.err.println(doc);
		
		//필요한 정보만 가져오기
		String cssSelector = "#yesWrap #ySchContSec #sGoodsWrap .sGoodsSec section#goodsListWrap div.sGoodsSecArea ul#yesSchList li div.itemUnit";
		
		//전체 가져왔던 데이터중 내가 필요한 정보만 불러오기
		Elements contents = doc.select(cssSelector);
		
		//확인용
		//System.out.println(contents);
		
		//리스트에 저장할 객체 생성
		List<Yes24BookCrawl> bookCrawls = new ArrayList<>();
		
		for(Element content : contents) {
			String name = content.select("div.item_info div.info_row.info_name a.gd_name").text();
			String priceString = content.select("div.item_info div.info_row.info_price strong.txt_num em.yes_b").text();			
			 // 숫자만 추출하여 변환
			int price = Integer.parseInt(priceString.replaceAll("[^0-9]", ""));
			String viewDetail = content.select("div.item_img div.img_canvas span.img_item span.img_grp a.lnk_img").attr("href");
			
			// 이미지 크롤링
			Element imgElement = content.select("div.item_img div.img_canvas span.img_item span.img_grp a.lnk_img em.img_bdr img.lazy").first();
			String imageUri = imgElement.attr("data-original");
			
			Yes24BookCrawl bookCrawl = Yes24BookCrawl.builder()
					.bookName(name)
					.bookPrice(price)
					.viewDetail("https://yes24.com"+viewDetail)
					.imageName(imageUri)
					.build();
			System.out.println(bookCrawl.toString());
			
			bookCrawls.add(bookCrawl);
		}
		
		//JPA로 DB에 저장
		saveYes24BookCrawls(bookCrawls);
	}
	
	//전체 출력해줄 메서드
	public List<Yes24BookCrawl> getAllBooks() {
		return crawlRepository.findAll();
	}
	
    
    
}

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
import com.web.persistence.Yes24CrawlRepository;

@Service
public class Yes24CrawlService {
	
	@Autowired
	private Yes24CrawlRepository crawlRepository;
	
	public Yes24CrawlService(Yes24CrawlRepository crawlRepository) {
        this.crawlRepository = crawlRepository;
    }

    public void saveYes24BookCrawl(Yes24BookCrawl bookCrawl) {
        crawlRepository.save(bookCrawl);
    }

    public void saveYes24BookCrawls(List<Yes24BookCrawl> bookCrawls) {
        crawlRepository.saveAll(bookCrawls);
    }
	
    // 페이지 번호에 따라 검색 URL을 생성하는 메소드 수정
    public String generateSearchUrl(int page) {
        try {
            String searchQuery = "정보처리기사";
            String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8.toString());
            return "https://www.yes24.com/Product/Search?domain=ALL&query=" + encodedQuery + "&page=" + page;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
	
//    @PostConstruct
    public void getBooktDates() throws IOException {
        List<Yes24BookCrawl> bookCrawls = new ArrayList<>();
        int maxPages = 4; // 최대 페이지 수 설정

        for (int page = 1; page <= maxPages; page++) {
            String Book_Data_URL = generateSearchUrl(page); // 페이지 번호를 인자로 넘겨주는 수정된 URL 생성 메소드

            Document doc = Jsoup.connect(Book_Data_URL).get(); // URL에 담긴 정보 전부 불러오기
            
            String cssSelector = "#yesWrap #ySchContSec #sGoodsWrap .sGoodsSec section#goodsListWrap div.sGoodsSecArea ul#yesSchList li div.itemUnit";

            // 페이지에 데이터가 존재하는지 체크
            if (doc.select(cssSelector).isEmpty()) {
            	System.out.println("===========데이터 없음 ==========");
                //break; // 현재 페이지에 데이터가 없으면 루프 중단
            }

            Elements contents = doc.select(cssSelector); // 필요한 정보만 가져오기
            System.out.println(contents);
            for (Element content : contents) {
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
                        .viewDetail("https://yes24.com" + viewDetail)
                        .imageName(imageUri)
                        .build();   

                bookCrawls.add(bookCrawl);
            }
        }

        // 모든 페이지 크롤링 후 DB에 저장
        saveYes24BookCrawls(bookCrawls);
    }
    

	
	
	//전체 출력해줄 메서드
	public List<Yes24BookCrawl> getAllBooks() {
		return crawlRepository.findAll();
	}
	
    
    
}

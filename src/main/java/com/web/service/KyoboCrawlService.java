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

import com.web.domain.KyoboBookCrawl;
import com.web.persistence.KyoboCrawlRepository;

@Service
public class KyoboCrawlService {

	@Autowired
	private KyoboCrawlRepository KyoboRepo;

	public KyoboCrawlService() {
		// 디폴트 생성자
	}

	public KyoboCrawlService(KyoboCrawlRepository kyoboRepo) {
		this.KyoboRepo = kyoboRepo;
	}

	public void saveKyoboCrawl(KyoboBookCrawl bookCrawl) {
		KyoboRepo.save(bookCrawl);
	}

	public void saveKyoboCrawls(List<KyoboBookCrawl> bookCrawl) {
		KyoboRepo.saveAll(bookCrawl);
	}

	public String generateSearchUrl(int page) {
		try {
			String seachQuery = "정보처리기사";
			String encodeQuery = URLEncoder.encode(seachQuery, StandardCharsets.UTF_8.toString());

			System.out.println("추출성");
			return "https://search.kyobobook.co.kr/search?keyword=" + encodeQuery + "&gbCode=TOT&target=total";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("추출실패");
			return null;
		}
	}

	//@PostConstruct
	public void getBookData() throws IOException {

		List<KyoboBookCrawl> bookCrawls = new ArrayList<>();
		int maxPages = 4;

		for (int page = 1; page <= maxPages; page++) {
			String KYOBO_URL = generateSearchUrl(page);

			Document doc = Jsoup.connect(KYOBO_URL).get();

			String cssSelector = ".wrapper.contents_search_result .container_wrapper #contents .contents_inner .search_result_top .tab_wrap type_line justify ui-tabs ui-corner-all ui-widget ui-widget-content #tabSearch .search_result_wrap .result_area .switch_prod_wrap.view_type_list ul.prod_list li.prod_item";
			System.out.println(cssSelector);
			
			// 페이지에 데이터가 존재하는지 체크
            if (doc.select(cssSelector).isEmpty()) {
            	System.out.println("===========데이터 없음 ==========");
                //break; // 현재 페이지에 데이터가 없으면 루프 중단
            }
            
            Elements contents = doc.select(cssSelector);
            System.out.println(contents);
            for(Element content : contents) {
            	String name = content.select(".prod_area.horizontal .prod_info_box .auto_overflow_wrap.prod_name_group .auto_overflow_contents .auto_overflow_inner .prod_info").text();
                String priceString = content.select(".prod_price .price .val").text();
                int price = Integer.parseInt(priceString.replaceAll("[^0-9]", ""));
                
                KyoboBookCrawl bookCrawl = KyoboBookCrawl.builder()
                		.bookName(name)
                		.bookPrice(price)
                		.build();
                
                bookCrawls.add(bookCrawl);
            }
		}
		saveKyoboCrawls(bookCrawls);

	}

}

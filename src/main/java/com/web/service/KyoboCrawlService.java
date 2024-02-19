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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.domain.AladinBookCrawl;
import com.web.domain.KyoboBookCrawl;
import com.web.persistence.KyoboCrawlRepository;

@Service
public class KyoboCrawlService {

	@Autowired
	private KyoboCrawlRepository KyoboRepo;


	public KyoboCrawlService(KyoboCrawlRepository kyoboRepo) {
		this.KyoboRepo = kyoboRepo;
	}

	public void saveKyoboCrawls(List<KyoboBookCrawl> bookCrawls) {
	    for (KyoboBookCrawl bookCrawl : bookCrawls) {
	        KyoboRepo.save(bookCrawl);
	    }
	}

	public String generateSearchUrl(int page, String jmfldnm) {
		try {
			String seachQuery = jmfldnm;
			String encodeQuery = URLEncoder.encode(seachQuery, StandardCharsets.UTF_8.toString());

			System.out.println("추출성공");
			return "https://search.kyobobook.co.kr/search?keyword=" + encodeQuery + "&target=total&gbCode=TOT&page=" + page;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("추출실패");
			return null;
		}
	}


	public List<KyoboBookCrawl> getBooksFromCrawl(String jmfldnm) throws IOException {
		List<KyoboBookCrawl> bookCrawls = new ArrayList<>();
		int maxPages = 4;

		for (int page = 1; page <= maxPages; page++) {
			String KYOBO_URL = generateSearchUrl(page, jmfldnm);

			Document doc = Jsoup.connect(KYOBO_URL).get();

			
			String cssSelector = ".wrapper.contents_search_result .container_wrapper .contents_wrap .contents_inner .search_result_top #tabSearch .search_result_wrap .result_area #shopData_list";
			String css = "div#shopData_list ul.prod_list";
			// 페이지에 데이터가 존재하는지 체크
			if (doc.select(cssSelector).isEmpty()) {
				System.out.println("===========데이터 없음 ==========");
				// break; // 현재 페이지에 데이터가 없으면 루프 
			}
			

				Elements contents = doc.select(css);
				//System.out.println(contents + "-=-=-=-=-=-=");
				for (Element content : contents) {
				    // 책 정보
				    Elements aTags = content.select("li.prod_item div.prod_area.horizontal div.prod_info_box div.auto_overflow_wrap.prod_name_group div.auto_overflow_contents div.auto_overflow_inner a.prod_info");

				    // 가격 정보
				    Elements priceElements = content.select("li.prod_item div.prod_area.horizontal div.prod_info_box div.prod_price");

				    // 이미지 정보
				    Elements imageElements = content.select("li.prod_item span.form_chk.no_label input.result_checkbox.spec_checkbox");

				    for (int i = 0; i < Math.min(Math.min(aTags.size(), imageElements.size()), priceElements.size()); i++) {
				        Element aTag = aTags.get(i);
				        Element priceElement = priceElements.get(i);
				        Element imageElement = imageElements.get(i);

				        // a 태그의 href 속성 값 (이미지 링크)
				        String imageLink = aTag.attr("href");

				        // a 태그의 id 속성 값을 가져옴
				        String idValue = aTag.select("span[id]").attr("id");

				        // 각 span에서 텍스트를 가져와서 처리
				        String name = aTag.select("span#" + idValue).text();
				        System.out.println("교보" + name);

				        // 가격
				        String price = priceElement.select("span.price span.val").text();
				        System.out.println(price);

				        // 이미지정보
				        String imageValue = imageElement.attr("data-bid");
				        String imageName = "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/"+imageValue+".jpg";
				        KyoboBookCrawl bookCrawl = KyoboBookCrawl.builder()
				                .bookName(name)
				                .bookPrice(price)
				                .viewDetail(imageLink)
				                .imageName(imageName)
				                .build();
				        bookCrawls.add(bookCrawl);
				    }
				}

			
		}
		return bookCrawls;

	}
	

	
	//전체 출력해줄 메서드
	public List<KyoboBookCrawl> getAllBooks(){
		Sort sortByDatabaseOrder = Sort.by(Sort.Direction.ASC, "id");
		return KyoboRepo.findAll(sortByDatabaseOrder);
	}
	
//============================================================
	public String SearchUrl(int page, String search) {
		try {
			String seachQuery = search;
			String encodeQuery = URLEncoder.encode(seachQuery, StandardCharsets.UTF_8.toString());

			System.out.println("추출성공");
			return "https://search.kyobobook.co.kr/search?keyword=" + encodeQuery + "&target=total&gbCode=TOT&page=" + page;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("추출실패");
			return null;
		}
	}


	public List<KyoboBookCrawl> getSearchBooksFromCrawl(String search) throws IOException {
		List<KyoboBookCrawl> bookCrawls = new ArrayList<>();
		int maxPages = 4;

		for (int page = 1; page <= maxPages; page++) {
			String KYOBO_URL = SearchUrl(page, search);

			Document doc = Jsoup.connect(KYOBO_URL).get();

			
			String cssSelector = ".wrapper.contents_search_result .container_wrapper .contents_wrap .contents_inner .search_result_top #tabSearch .search_result_wrap .result_area #shopData_list";
			String css = "div#shopData_list ul.prod_list";
			// 페이지에 데이터가 존재하는지 체크
			if (doc.select(cssSelector).isEmpty()) {
				System.out.println("===========데이터 없음 ==========");
				// break; // 현재 페이지에 데이터가 없으면 루프 
			}
			

				Elements contents = doc.select(css);
				//System.out.println(contents + "-=-=-=-=-=-=");
				for (Element content : contents) {
				    // 책 정보
				    Elements aTags = content.select("li.prod_item div.prod_area.horizontal div.prod_info_box div.auto_overflow_wrap.prod_name_group div.auto_overflow_contents div.auto_overflow_inner a.prod_info");

				    // 가격 정보
				    Elements priceElements = content.select("li.prod_item div.prod_area.horizontal div.prod_info_box div.prod_price");

				    // 이미지 정보
				    Elements imageElements = content.select("li.prod_item span.form_chk.no_label input.result_checkbox.spec_checkbox");

				    for (int i = 0; i < Math.min(Math.min(aTags.size(), imageElements.size()), priceElements.size()); i++) {
				        Element aTag = aTags.get(i);
				        Element priceElement = priceElements.get(i);
				        Element imageElement = imageElements.get(i);

				        // a 태그의 href 속성 값 (이미지 링크)
				        String imageLink = aTag.attr("href");

				        // a 태그의 id 속성 값을 가져옴
				        String idValue = aTag.select("span[id]").attr("id");

				        // 각 span에서 텍스트를 가져와서 처리
				        String name = aTag.select("span#" + idValue).text();
				        System.out.println("교보" + name);

				        // 가격
				        String price = priceElement.select("span.price span.val").text();
				        System.out.println(price);

				        // 이미지정보
				        String imageValue = imageElement.attr("data-bid");
				        String imageName = "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/"+imageValue+".jpg";
				        KyoboBookCrawl bookCrawl = KyoboBookCrawl.builder()
				                .bookName(name)
				                .bookPrice(price)
				                .viewDetail(imageLink)
				                .imageName(imageName)
				                .build();
				        bookCrawls.add(bookCrawl);
				    }
				}

			
		}
		return bookCrawls;

	}

}

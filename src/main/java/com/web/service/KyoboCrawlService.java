package com.web.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
	
	public KyoboCrawlService(KyoboBookCrawl bookCrawl) {
		KyoboRepo.save(bookCrawl);
	}
	
	public KyoboCrawlService(List<KyoboBookCrawl> bookCrawl) {
		KyoboRepo.saveAll(bookCrawl);
	}

	public String generateSearchUrl() {
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
	
	@PostConstruct
	public void getBookData() throws IOException {

		String KYOBO_URL = generateSearchUrl();

		Document doc = Jsoup.connect(KYOBO_URL).get();
//		System.out.println(doc);
		String cssSelector = "div.";
		
	}

}

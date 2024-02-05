package com.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.Crawler.WebCrawler;

@RestController
public class Controller {
	
	private final WebCrawler webCrawler;
	
	public Controller(WebCrawler webCrawler) {
		this.webCrawler = webCrawler;
	}
	
	
	@GetMapping("/grouptest")
	public String test(@RequestParam String test) {
		System.out.println("바qasdasd");
		System.out.println(test);
		System.out.println("woo Test");
		System.out.println("택승");
		System.out.println("다시해봅시다");
		System.out.println("다시해봅시다ㅇㅇㅇㅇ");
		System.out.println("변경");
		System.out.println("변경dsklfjweiotghjweiogjseiohg");
		return test;
	}
	
//	@GetMapping("/crawl")
//	public void crawl() {
//		webCrawler.crawl();
//	}
	


}

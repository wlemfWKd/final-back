package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.AladinBookCrawl;
import com.web.domain.KyoboBookCrawl;
import com.web.domain.Yes24BookCrawl;
import com.web.service.AladinCrawlService;
import com.web.service.KyoboCrawlService;
//import com.web.service.Yes24CrawlService;
import com.web.service.Yes24CrawlService;

@RestController
@RequestMapping("/detail")
public class CrawlController {

	@Autowired
	private Yes24CrawlService bookCrawlService;
	
	@Autowired
	private KyoboCrawlService kyoboCrawlService;
	
	@Autowired
	private AladinCrawlService aladinCrawlService;
	
	@GetMapping("/books")
    public ResponseEntity<List<Yes24BookCrawl>> getBooks() {
        List<Yes24BookCrawl> books = bookCrawlService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
	
	@GetMapping("/kyoboBooks")
	public ResponseEntity<List<KyoboBookCrawl>> getkyoboBooks(){
		List<KyoboBookCrawl> kyoboBooks = kyoboCrawlService.getAllBooks();
		return new ResponseEntity<>(kyoboBooks, HttpStatus.OK);
	}
	
	@GetMapping("/aladinBooks")
	public ResponseEntity<List<AladinBookCrawl>> getAladinBooks(){
		List<AladinBookCrawl> aladinBooks = aladinCrawlService.getAllBooks();
		return new ResponseEntity<>(aladinBooks,HttpStatus.OK);
	}
}

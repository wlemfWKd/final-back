package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Yes24BookCrawl;

public interface CrawlRepository extends JpaRepository<Yes24BookCrawl, String> {
	

}

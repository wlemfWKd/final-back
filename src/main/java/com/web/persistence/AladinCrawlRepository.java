package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.AladinBookCrawl;

public interface AladinCrawlRepository extends JpaRepository<AladinBookCrawl, Long> {

}

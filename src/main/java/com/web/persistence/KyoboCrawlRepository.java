package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.KyoboBookCrawl;

public interface KyoboCrawlRepository extends JpaRepository<KyoboBookCrawl, String> {

}

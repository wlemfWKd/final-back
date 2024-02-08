package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.WbDTO;

public interface WorkbookRepository extends JpaRepository<WbDTO, String>{

}

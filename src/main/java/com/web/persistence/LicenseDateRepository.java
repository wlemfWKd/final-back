package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.LicenseDate;

@Repository
public interface LicenseDateRepository extends JpaRepository<LicenseDate, String>{

}

package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.LicenseInfo;


@Repository
public interface InfoRepository extends JpaRepository<LicenseInfo, String>{


}

package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.LicenseAccess;

@Repository
public interface LicenseAccessRepository extends JpaRepository<LicenseAccess, String>{

}

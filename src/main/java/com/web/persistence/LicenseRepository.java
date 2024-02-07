package com.web.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.LicenseList;

@Repository
public interface LicenseRepository extends JpaRepository<LicenseList, String>{


}

package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.LicensRank;

@Repository
public interface RankRepository extends JpaRepository<LicensRank, Integer>{

}

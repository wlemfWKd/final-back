package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.CountingStar;

@Repository
public interface StarRepository extends JpaRepository<CountingStar, Long>{
	
	CountingStar findByJmnmAndUsername(String jmnm, String username);
}

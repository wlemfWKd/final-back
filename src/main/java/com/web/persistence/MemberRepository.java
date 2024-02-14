package com.web.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.web.domain.Member;
import com.web.domain.SocialType;

public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findByUsername(String username);

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

	Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String id);

	Member findByEmail(String email);
	
	Optional<Member> findByRefreshToken(String refreshToken);
	
	Member findByPhoneNum(String data);
}

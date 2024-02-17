package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.Member;
import com.web.jwt.JWTUtil;
import com.web.persistence.MemberRepository;

@Service
public class TokenService {
	
	@Autowired
	MemberRepository mRepo;
	
	private final JWTUtil jwtUtil;
    public TokenService(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    public boolean existMember(String token) {
    	if(token != null && token.startsWith("Bearer ")) {
    		System.out.println("여기까지는 왔니??");
    		return true;
    	}else {
    		return false;
    	}
    }
	
	public Member getMember(String token) {
        String jwtToken = token.substring(7);
        
        String email = jwtUtil.getEmail(jwtToken);
        Member member = mRepo.findByEmail(email);
        
        return member;
	}
	public Member getMemberByMemberNum(String token) {
		String jwtToken = token.substring(7);
		System.out.println("확인좀"+jwtToken);
		
		String memberE = jwtUtil.getEmailopt(jwtToken).get();
		System.out.println("했냐????????????");
		
		Member member = mRepo.findByEmail(memberE);
		
		
		System.out.println(member);
		return member;
	}
	
}

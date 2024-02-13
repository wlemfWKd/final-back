package com.web.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.domain.Member;
import com.web.domain.Role;
import com.web.dto.JoinDTO;
import com.web.repository.MemberRepository;

@Service
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();

        Boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {

            return;
        }
        
        Member member = Member.builder()
        		.username(username)
        		.password(bCryptPasswordEncoder.encode(password))
        		.role(Role.USER)
        		.email(email)
        		.build();

        memberRepository.save(member);
    }
}

package com.web.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.web.domain.Member;
import com.web.domain.Role;
import com.web.jwt.dto.CustomUserDetails;
import com.web.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	
	private static final String NO_CHECK_URL = "/login";

    private final JWTUtil jwtUtil;
    
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    
    private final MemberRepository mRepo;
    
    public JwtFilter(JWTUtil jwtUtil, MemberRepository mRepo) {
        this.jwtUtil = jwtUtil;
        this.mRepo = mRepo;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getRequestURI().equals(NO_CHECK_URL)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//request에서 Authorization 헤더 찾기
		String authorization= request.getHeader(jwtUtil.getAccessHeader());
//		String refreshAuth = request.getHeader(jwtUtil.getRefreshHeader());
//		System.out.println(authorization+" Authorization");
//		//Authorization 헤더 검증
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//
//            System.out.println("token null");
//            filterChain.doFilter(request, response);
//						
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }
        
        String refreshToken = jwtUtil.extractRefreshToken(request)
                .filter(jwtUtil::isTokenValid)
                .orElse(null);
        
        if(refreshToken != null) {
        	checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
        	return;
        }
        
        if (refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }        
        
        
//        System.out.println("authorization now");
//        //Bearer 부분 제거 후 순수 토큰만 획득
//        String token = authorization.split(" ")[1];
//        
//		//토큰 소멸 시간 검증
//        if (jwtUtil.isExpired(token)) {
//
//            System.out.println("token expired");
//            filterChain.doFilter(request, response);
//
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }
        
		//토큰에서 username과 role 획득
//        String username = jwtUtil.getUsername(token);
//        Role role = jwtUtil.getRole(token);
//        
//        Member member = Member.builder()
//        		.username(username)
//        		.password("fefslivhh")
//        		.role(role)
//        		.build();
				

//        Member memberEntity = new Member();
//        memberEntity.setUsername(username);
//        memberEntity.setPassword("temppassword");
//        memberEntity.setRole(role);
        
		//UserDetails에 회원 정보 객체 담기
//        CustomUserDetails customUserDetails = new CustomUserDetails(member);
//
//        //스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//        //세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        filterChain.doFilter(request, response);       
	}
	
	//리프레시 토큰을 확인하고 리프레시 토큰이 있는 경우 엑세스토큰/리프레시 토큰 재발급
	public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
	    mRepo.findByRefreshToken(refreshToken)
	            .ifPresent(member -> {
	                String reIssuedRefreshToken = reIssueRefreshToken(member);
	                jwtUtil.sendAccessAndRefreshToken(response, jwtUtil.createAccessToken(member.getEmail()),
	                        reIssuedRefreshToken);
	            });
	}
	
	//리프레시 토큰 재발급
    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtUtil.createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        mRepo.saveAndFlush(member);
        return reIssuedRefreshToken;
    }
    
    //엑세스 토큰 확인하고 인증하기
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
		log.info("checkAccessTokenAndAuthentication() 호출");
		jwtUtil.extractAccessToken(request)
		.filter(jwtUtil::isTokenValid)
		.ifPresent(accessToken -> jwtUtil.getEmailOpt(accessToken)
		.ifPresent(email -> Optional.of(mRepo.findByEmail(email))
		.ifPresent(this::saveAuthentication)));
		
		filterChain.doFilter(request, response);
}    
    
    public void saveAuthentication(Member myMember) {
        String password = myMember.getPassword();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myMember.getEmail())
                .password(password)
                .roles(myMember.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

package com.web.jwt;

import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.web.domain.Role;
import com.web.persistence.MemberRepository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
@Getter
@Slf4j
public class JWTUtil {
	
	@Autowired
	MemberRepository mRepo;

    private SecretKey secretKey;
    
    @Value("${spring.jwt.access.expriation}")
    private Long accessTokenExpirationPeriod;

    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;
    
    @Value("${spring.jwt.access.header}")
    private String accessHeader;

    @Value("${spring.jwt.refresh.header}")
    private String refreshHeader;   
    
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String BEARER = "Bearer ";
    
    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }
    
    public Optional<String> getEmailopt(String token) {
        try {
            return Optional.ofNullable(Jwts.parser()
                    .verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                    .get("email", String.class));
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }
 // 추가
    public Optional<Long> getMemberNum(String token) {
    	try {
    		return Optional.ofNullable(Jwts.parser()
    				.verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
    				.get("memberNum", Long.class));
    	}catch(Exception e) {
    		log.error("액세스 토큰이 유효하지 않습니다.");
    		return Optional.empty();
    	}
    }
    public String getEmail(String token) {
    	return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }
    

    public Role getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", Role.class);
    }

    public boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }
    
    
    

    //액세스 토큰 생성
    public String createAccessToken(String username, String role) {

        return Jwts.builder()
        		.subject(ACCESS_TOKEN_SUBJECT)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationPeriod))
                .signWith(secretKey)
                .compact();
    }
    
    public String createAccessToken(String email) {
    	return Jwts.builder()
    			.subject(ACCESS_TOKEN_SUBJECT)
    			.claim("email", email)
    			.issuedAt(new Date(System.currentTimeMillis()))
    			.expiration(new Date(System.currentTimeMillis() + accessTokenExpirationPeriod))
    			.signWith(secretKey)
    			.compact();
    }
    
    //리프레시 토큰 생성
    public String createRefreshToken() {
    	return Jwts.builder()
    			.subject(REFRESH_TOKEN_SUBJECT)
    			.expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
    			.signWith(secretKey)
    			.compact();
    }
    
    
    //AccessToken 헤더에 실어서 보내기
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    //AccessToken + RefreshToken 헤더에 실어서 보내기
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    public void updateRefreshToken(String email, String refreshToken) {
        Optional.of(mRepo.findByEmail(email))
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }
}

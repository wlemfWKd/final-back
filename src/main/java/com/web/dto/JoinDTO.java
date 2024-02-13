package com.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JoinDTO {
	private String username; 	// 아이디
	private String password;	// 비밀번호
	private String memberName;	// 이름
	private String email;		// 이메일
	private String domain;		// 도메인
	private String phoneNum;	// 전화번호
	private String socialNum1;	// 주민번호 앞자리 (생년월일)
	private String socialNum2;	// 주민번호 뒷자리 (성별)
	private String randomInitial; // 이메일 인증 코드
}

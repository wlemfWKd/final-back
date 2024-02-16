package com.web.service;

import java.security.Principal;
import java.util.Map;

import com.web.domain.JoinDTO;
import com.web.domain.Member;

public interface MemberService {
	// 아이디 중복확인
	public String checkId(String username);
	// 이메일 중복확인
	public String checkEmail(JoinDTO joinDTO);
	// 이메일 중복확인
	public String checkMemberEmail(JoinDTO joinDTO);
	// 인증코드 확인
	public String checkCode(JoinDTO joinDTO);
	// 회원가입 
	public String join(JoinDTO joinDTO);
	// 회원정보 수정
	public String editMemberInfo(JoinDTO joinDTO);
	// 필요할 때 토큰 넘겨서 username(아이디)로 멤버 정보 불러오기
	public Member getMemberInfo(String username);
	
	public Map<String, Object> findPwd(JoinDTO joinDTO);
	// 비밀번호 재설정
	public String editPwd(JoinDTO joinDTO);
	//현재 사용중인 사용자 정보 가져오기
	public Member getCurrentUser(Principal principal);
	
}

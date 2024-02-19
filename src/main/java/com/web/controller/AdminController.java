package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.Member;
import com.web.service.MemberService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MemberService ms;
	
	@GetMapping("/memberlist")
	public Page<Member> memberlist(@PageableDefault(size = 10) Pageable pageable) {
	    return ms.getMemberList(pageable);
	}
	
	
}

package com.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/grouptest")
	public String test(@RequestParam String test) {
		System.out.println("바qasdasd");
		System.out.println(test);
		System.out.println("woo Test");
		System.out.println("택승");
		System.out.println("다시해봅시다");
		System.out.println("다시해봅시다ㅇㅇㅇㅇ");
		return test;
	}


}

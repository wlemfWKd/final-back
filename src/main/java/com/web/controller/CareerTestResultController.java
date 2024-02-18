package com.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.entity.CareerTestResult;
import com.web.entity.CareerTestResultRequest;

@RestController
public class CareerTestResultController {

    @PostMapping("/career-test/result")
    public CareerTestResult submitCareerTestResult(@RequestBody CareerTestResultRequest request) {
        // 여기에 결과 처리 로직을 구현합니다.
        // request에서 필요한 정보를 추출하여 처리하고,
        // CareerTestResult 객체를 생성하여 반환합니다.
        CareerTestResult result = new CareerTestResult();
        // 결과 처리 로직 작성
        return result;
    }
}
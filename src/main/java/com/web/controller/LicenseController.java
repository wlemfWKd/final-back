package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.LicensRank;
import com.web.domain.LicenseInfo;
import com.web.domain.LicenseList;
import com.web.service.LicenseInfoService;
import com.web.service.LicenseRankService;


@RestController
@RequestMapping("/license")
public class LicenseController {

	@Autowired
	private com.web.service.LicenseListService LicenseListService;
	
	@Autowired
	private LicenseInfoService infoService;
	
	@Autowired LicenseRankService rankService;
	
	@GetMapping("/list")
	public ResponseEntity<List<LicenseList>> getLicenses() {
        List<LicenseList> licenses = LicenseListService.getAllLicenses();
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }
	
	@GetMapping("/info")
	public ResponseEntity<List<LicenseInfo>> getInfo() {
        List<LicenseInfo> info = infoService.getAllInfo();
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
	
	@GetMapping("/rank")
	public ResponseEntity<List<LicensRank>> getRank() {
        List<LicensRank> rank = rankService.getAllRanks();
        return new ResponseEntity<>(rank, HttpStatus.OK);
    }
	
}

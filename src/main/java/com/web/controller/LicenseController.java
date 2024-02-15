package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.LicensRank;
import com.web.domain.LicenseAccess;
import com.web.domain.LicenseDate;
import com.web.domain.LicenseInfo;
import com.web.domain.LicenseList;
import com.web.service.LicenseAccessService;
import com.web.service.LicenseDateService;
import com.web.service.LicenseInfoService;
import com.web.service.LicenseRankService;


@RestController
@RequestMapping("/license")
public class LicenseController {

	@Autowired
	private com.web.service.LicenseListService LicenseListService;
	
	@Autowired
	private LicenseInfoService infoService;
	
	@Autowired 
	private LicenseRankService rankService;
	
	@Autowired
	private LicenseAccessService acService;
	
	@Autowired
	private LicenseDateService dtService;
	
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
	
	@GetMapping("/access")
	public ResponseEntity<List<LicenseAccess>> getAc() {
        List<LicenseAccess> ac = acService.getAllAccess();
        return new ResponseEntity<>(ac, HttpStatus.OK);
    }
	
	@GetMapping("/date")
	public ResponseEntity<List<LicenseDate>> getDt() {
        List<LicenseDate> dt = dtService.getAllDate();
        return new ResponseEntity<>(dt, HttpStatus.OK);
    }
}

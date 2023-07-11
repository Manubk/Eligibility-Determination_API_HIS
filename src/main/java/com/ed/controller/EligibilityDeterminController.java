package com.ed.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ed.dto.EligibilityDeterminDto;
import com.ed.serviceinterface.IEligibilityDeterminService;

@CrossOrigin
@RestController
public class EligibilityDeterminController {
	
	
	private static final Logger log = LoggerFactory.getLogger(EligibilityDeterminController.class);

	@Autowired
	private IEligibilityDeterminService eligibilityService;
	
	@GetMapping("/eligibility/{caseNum}")
	public ResponseEntity<EligibilityDeterminDto> findEligibility(@PathVariable Long caseNum){
		log.info("findEligibility caseNum = "+caseNum);
		
		EligibilityDeterminDto elgDtlDto = eligibilityService.saveEligDtl(caseNum);
		
		return new ResponseEntity<EligibilityDeterminDto>(elgDtlDto,HttpStatus.OK);
	}
	
	@GetMapping("/eligibility/applicant/{appId}")
	public ResponseEntity<List<EligibilityDeterminDto>> findEligibilityByAppId(@PathVariable Long appId){
		log.info("findELigibilityByAppId appId = "+appId);
		
		List<EligibilityDeterminDto> eligibilityDtos = eligibilityService.findEligibilityByAppId(appId);
		
		return (eligibilityDtos != null )? new ResponseEntity<List<EligibilityDeterminDto>>(eligibilityDtos,HttpStatus.OK):
			new ResponseEntity<List<EligibilityDeterminDto>>(eligibilityDtos,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

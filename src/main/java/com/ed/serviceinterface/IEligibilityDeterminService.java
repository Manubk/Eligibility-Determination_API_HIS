package com.ed.serviceinterface;

import org.springframework.stereotype.Service;

import com.ed.dto.EligibilityDeterminDto;

@Service
public interface IEligibilityDeterminService {
	
	public EligibilityDeterminDto saveEligDtl(Long CaseNum);
}

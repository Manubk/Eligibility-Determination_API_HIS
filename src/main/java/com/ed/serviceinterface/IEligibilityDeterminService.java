package com.ed.serviceinterface;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ed.dto.EligibilityDeterminDto;

@Service
public interface IEligibilityDeterminService {
	
	public EligibilityDeterminDto saveEligDtl(Long CaseNum);
	
	public List<EligibilityDeterminDto> findEligibilityByAppId(Long appId);
}

package com.ed.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EligibilityDeterminDto {
	
	private Long eligDeterminId;
	
	private String planName;
	
	private String planStatus;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;
	
	private Double benefitAmount;

	private String denialReason;
	
	private Long caseNum;
	
	
	
	
}

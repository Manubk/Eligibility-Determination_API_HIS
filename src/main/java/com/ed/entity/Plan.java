package com.ed.entity;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

import com.ed.constants.AppConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PLAN_MASTER")
public class Plan {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PLAN_ID")
	private Integer planId;
	
	@Column(name = "PLAN_NAME")
	private String planName;
	
	@Column(name = "BENEFIT_AMT")
	private double benefitAmt;
	
	@Column(name = "PLAN_START_DATE")
	private LocalDate planStartDate;
	
	@Column(name = "PLAN_END_DATE")
	private LocalDate planEndDate;
	
	@Column(name = "PLAN_CATEGORY_ID")
	private Integer planCategoryId;
	
	@Column(name = "ACTIVE_SW" , columnDefinition = "varchar(1) default 'A'")
	private String activeSw = AppConstants.ACTIVATE;
	
	@Column(name = "INCOME_LIMIT")
	public Double incomeLimit;
	
}

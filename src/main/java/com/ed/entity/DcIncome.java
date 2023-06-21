package com.ed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "DC_INCOME")
public class DcIncome {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "INCOME_ID")
	private Long incomeId;
	
	@Column(name = "CASE_NUM")
	private Long caseNum;
	
	@Column(name = "EMP_INCOME")
	private Double empIncome;
	
	@Column(name = "RENT_INCOME")
	private Double rentIncome;
	
	@Column(name = "PROPERTY_INCOME")
	private Double propertyIncome;
}

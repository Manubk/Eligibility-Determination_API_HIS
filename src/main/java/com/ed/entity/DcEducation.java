package com.ed.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class DcEducation {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name = "EDU_ID")
	private Long eduId;
	
	@Column(name = "CASE_NUM")
	private Long caseNum;
	
	@Column(name = "HIGHEST_QUAL")
	private String highestQual;
	
	@Column(name = "GRAD_YEAR")
	private LocalDate gradYear;

	@Column(name = "UNIVERSITY_NAME")
	private String universityName;
}

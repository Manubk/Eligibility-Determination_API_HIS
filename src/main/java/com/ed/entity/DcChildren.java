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
@Table(name = "DC_CHILDREN")
public class DcChildren {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name = "CHILDREN_ID")
	private Long childrenId;
	
	@Column(name = "CASE_NUM")
	private Long caseNum;
	
	@Column(name = "CHILDREN_DOB")
	private LocalDate childrenDob;
	
	@Column(name = "CHILDREN_SSN")
	private Long ssn;
}

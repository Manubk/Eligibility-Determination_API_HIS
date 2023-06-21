package com.ed.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.entity.DcIncome;
import java.util.List;


@Repository
public interface DcIncomeRepo extends JpaRepository<DcIncome, Serializable> {
	
	public DcIncome findByCaseNum(Long caseNum);
}

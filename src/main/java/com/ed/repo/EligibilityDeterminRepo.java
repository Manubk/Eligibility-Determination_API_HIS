package com.ed.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.entity.EligibilityDeterminEntity;

@Repository
public interface EligibilityDeterminRepo  extends JpaRepository<EligibilityDeterminEntity, Long>{

	public EligibilityDeterminEntity findByCaseNum(Long caseNum);
}

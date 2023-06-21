package com.ed.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.entity.DcEducation;
import java.util.List;


@Repository
public interface DcEducationRepo extends JpaRepository<DcEducation , Serializable> {
	
	DcEducation findByCaseNum(Long caseNum);
}

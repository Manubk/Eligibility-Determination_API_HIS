package com.ed.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.entity.DcChildren;
import java.util.List;


@Repository
public interface DcChildrenRepo extends JpaRepository<DcChildren, Serializable> {
	
	List<DcChildren> findByCaseNum(Long caseNum);
}

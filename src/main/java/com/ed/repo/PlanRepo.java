package com.ed.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.entity.Plan;

@Repository
public interface PlanRepo extends JpaRepository<Plan, Serializable> {
	
}

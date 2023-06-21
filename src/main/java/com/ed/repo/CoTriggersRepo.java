package com.ed.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.entity.CoTriggers;

@Repository
public interface CoTriggersRepo extends JpaRepository<CoTriggers, Long>{

}

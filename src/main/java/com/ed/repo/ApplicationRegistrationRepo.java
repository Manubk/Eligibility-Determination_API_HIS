package com.ed.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.entity.ApplicationRegistration;

@Repository
public interface ApplicationRegistrationRepo extends JpaRepository<ApplicationRegistration, Long> {
}

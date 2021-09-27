package com.metanet.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.metanet.intern.domain.Education; 

public interface EducatinoRepository extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education>{

}

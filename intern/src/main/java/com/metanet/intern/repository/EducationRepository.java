package com.metanet.intern.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.metanet.intern.domain.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {

	Page<Education> findByIsDeleted(Integer isNotDeleted, Pageable pageable);

}

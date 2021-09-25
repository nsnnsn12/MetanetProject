package com.metanet.intern.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.metanet.intern.domain.Major;

public interface MajorRepository extends JpaRepository<Major, Long> {
	Page<Major> findByIsDeleted(Integer isNotDeleted,Pageable pageable);
	
}

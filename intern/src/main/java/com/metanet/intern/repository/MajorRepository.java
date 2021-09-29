package com.metanet.intern.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Student;

public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {
	Page<Major> findByIsDeleted(Integer isNotDeleted,Pageable pageable);
	//1:삭제
	List<Major> findByIsDeleted(Integer isDeleted);
	
}

package com.metanet.intern.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>, JpaSpecificationExecutor<Manager>{
	List<Manager> findByLoginId(String loginId);
	Manager findByLoginIdAndIsDeleted(String loginId, Integer isDeleted);
	Page<Manager> findByIsDeleted(Integer isDeleted, Pageable pageable);
	
	List<Manager> findByIsDeletedAndMajor(Integer isDeleted, Major major);
}

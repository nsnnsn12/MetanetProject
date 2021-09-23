package com.metanet.intern.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;
import com.metanet.intern.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
	List<Manager> findByLoginId(String loginId);
	Manager findByLoginIdAndIsDeleted(String loginId, Integer isDeleted);
	Page<Manager> findByIsDeleted(Integer isDeleted, Pageable pageable);
}

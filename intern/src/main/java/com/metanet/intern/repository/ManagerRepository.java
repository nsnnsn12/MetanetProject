package com.metanet.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.metanet.intern.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
	List<Manager> findByLoginId(String loginId);
}

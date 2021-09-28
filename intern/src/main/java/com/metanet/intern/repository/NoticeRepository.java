package com.metanet.intern.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> ,JpaSpecificationExecutor<Notice>{

	Page<Notice> findByIsDeleted(Integer notDeleted, Pageable pageable);



}

package com.metanet.intern.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Lecture;
import com.metanet.intern.vo.LectureListJoin;

public interface LectureRepository extends JpaRepository<Lecture,Long>, JpaSpecificationExecutor<Lecture>{

	@Query(value="SELECT a.lecture_id as id, b.NAME as professorName, c.NAME as educationTitle, c.CODE as educationCode, d.title as majorTitle FROM LECTURE a \r\n"
			+ "JOIN MANAGER b ON a.MANAGER_ID = b.MANAGER_ID\r\n"
			+ "JOIN education c ON c.EDUCATION_ID = a.EDUCATION_ID\r\n"
			+ "JOIN major d ON d.major_id = c.major_id \r\n"
			+ "WHERE a.is_deleted =:isNotDeleted",
			countQuery = "SELECT count(*) FROM LECTURE a \r\n"
					+ "JOIN MANAGER b ON a.MANAGER_ID = b.MANAGER_ID\r\n"
					+ "JOIN education c ON c.EDUCATION_ID = a.EDUCATION_ID\r\n"
					+ "JOIN major d ON d.major_id = c.major_id \r\n"
					+ "WHERE a.is_deleted =:isNotDeleted", 
			nativeQuery=true)
	Page<LectureListJoin> findAllEducations(@Param("isNotDeleted") Integer isNotDeleted, Pageable pageable);

}

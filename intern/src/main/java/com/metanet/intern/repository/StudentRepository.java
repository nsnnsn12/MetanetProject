package com.metanet.intern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metanet.intern.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	//학번조회
	List<Student> findByStudentNumber(Integer studentNumber);

}
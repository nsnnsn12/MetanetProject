package com.metanet.intern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.domain.Student;
import com.metanet.intern.repository.PhotoRepository;
import com.metanet.intern.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	PhotoRepository photoRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	private final StorageService storageService;

	@Autowired
	public StudentService(StorageService storageService) {
		this.storageService = storageService;
	}
	
	// 목록 조회
	public List<Student> list(){
		return studentRepository.findAll();
	}

	// 등록
	public Long join(Student student) {
		//중복검사
		validationDuplicateLoginId(student);
		
		//비밀번호 인코딩 후 저장
		student.setPassword(passwordEncoder.encode(student.getPassword()));

		// 파일저장
		PhotoFile photoFile = storageService.photoStore(student.getImage());

		//
		if (photoFile != null) {
			photoRepository.save(photoFile);
			student.setPhoto_id(photoFile);
		}
		studentRepository.save(student);
		return student.getId();
	}

	// 중복검사
	private void validationDuplicateLoginId(Student student) {
		List<Student> students = studentRepository.findByStudentNumber(student.getStudentNumber());
		if (!students.isEmpty()) {
			throw new IllegalStateException("존재하는 아이디입니다.");
		}
	}

}

package com.metanet.intern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.domain.Student;
import com.metanet.intern.repository.MajorRepository;
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

	@Autowired
	MajorRepository majorRepository;

	private final StorageService storageService;

	@Autowired
	public StudentService(StorageService storageService) {
		this.storageService = storageService;
	}

	// 목록 조회
	public Page<Student> list(Pageable pageable) {
		return studentRepository.findByIsDeleted(0, pageable);
	}

	// 등록
	public Long join(Student student) {
		// 중복검사
		validationDuplicateLoginId(student);

		// 비밀번호 인코딩 후 저장
		student.setPassword(passwordEncoder.encode(student.getPassword()));

		// 사진파일 저장
		PhotoFile photoFile = storageService.photoStore(student.getImage());
		if (photoFile != null) {
			photoRepository.save(photoFile);
			student.setPhoto(photoFile);
		}

		// 전공 검색
		student.setMajor(majorRepository.findById(student.getMajorId()).get());

		// 데이터베이스 저장
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

	// 수정
	public Long modify(Student student) {
		// 사진파일 저장
		PhotoFile photoFile = storageService.photoStore(student.getImage());
		if (photoFile != null) {
			photoRepository.save(photoFile);
			student.setPhoto(photoFile);
		}

		// 전공 검색
		student.setMajor(majorRepository.findById(student.getMajorId()).get());

		// 데이터베이스 수정
		studentRepository.save(student);

		return student.getId();
	}

}

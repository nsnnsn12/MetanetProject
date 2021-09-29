package com.metanet.intern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.format.InputAccessor.Std;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.domain.Student;
import com.metanet.intern.repository.MajorRepository;
import com.metanet.intern.repository.PhotoRepository;
import com.metanet.intern.repository.StudentRepository;
import com.metanet.intern.spec.ManagerSpec;
import com.metanet.intern.spec.StudentSpec;
import com.metanet.intern.vo.StudentSearchCondition;

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

	// 목록 검색 조회
	public Page<Student> searchList(Pageable pageable, StudentSearchCondition condition) {
		Specification<Student> spec = Specification.where(StudentSpec.isNotDeleted());
		
		if(!condition.getType().isBlank() && !condition.getText().isBlank()) {
			if(condition.getType().equals("name")) {
				spec = spec.and(StudentSpec.likeName(condition.getText()));
			}else {
				spec = spec.and(StudentSpec.likeStudentNumber(condition.getText()));
			}
		}
		
		if(condition.getMajorFilter() != null) {
			spec = spec.and(StudentSpec.equalMajor(condition.getMajorFilter()));
		}
		
		return studentRepository.findAll(spec, pageable);
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
	public Long modify(Student tempStudent) {
		Student student = studentRepository.getById(tempStudent.getId());	
		// 사진파일 저장
		PhotoFile photoFile = storageService.photoStore(tempStudent.getImage());
		if (photoFile != null) {
			photoRepository.save(photoFile);
			student.setPhoto(photoFile);
		}

		//이름, 전화번호, 생년월일, 전공, 학적상태, 학
		student.setName(tempStudent.getName());
		student.setTelNo(tempStudent.getTelNo());
		student.setBirth(tempStudent.getBirth());
		student.setMajor(majorRepository.findById(tempStudent.getMajorId()).get());
		student.setStatus(tempStudent.getStatus());
		student.setSemester(tempStudent.getSemester());


		// 데이터베이스 수정
		studentRepository.save(student);

		return student.getId();
	}

	// 삭제
	public Long delete(Long id) {
		Student student =  studentRepository.getById(id);
		
		// 삭제 처리
		student.setIsDeleted(1);

		return student.getId();
	}

}

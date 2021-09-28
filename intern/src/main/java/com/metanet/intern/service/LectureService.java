package com.metanet.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Lecture;
import com.metanet.intern.repository.LectureRepository;
import com.metanet.intern.spec.EducationSpec;
import com.metanet.intern.spec.LectureSpec;
import com.metanet.intern.vo.LectureListJoin;
import com.metanet.intern.vo.LectureSearchCondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class LectureService {
	@Autowired
	LectureRepository lectureRepository;
	
	final Integer isNotDeleted = 0;
	public Page<LectureListJoin> findAllEducations(Pageable pageable){
		return lectureRepository.findAllEducations(isNotDeleted, pageable);
	};
	
	public void create(Lecture lecture) {
		lecture.setLectureProcCount(0);
		lectureRepository.save(lecture);
	}

	public Page<Lecture> searchLectureList(Pageable pageable, LectureSearchCondition condition) {
		log.info("검색실행");
		Specification<Lecture> spec = Specification.where(LectureSpec.isNotDeleted());
		if(!condition.getType().isBlank() && !condition.getText().isBlank()) {
			if(condition.getType().equals("title")) {
				spec = spec.and(LectureSpec.likeTitle(condition.getText()));
			}else if(condition.getType().equals("code")){
				spec = spec.and(LectureSpec.likeCode(condition.getText()));
			}else {
				spec = spec.and(LectureSpec.likeProfessor(condition.getText()));
			}
		}
		
		if(condition.getCredit() != null) {
			spec = spec.and(LectureSpec.equalCredit(condition.getCredit()));
		}
		
		if(condition.getDivision() != null) {
			spec = spec.and(LectureSpec.equalDivision(condition.getDivision()));
		}
		
		if(condition.getMajor() != null) {
			spec = spec.and(LectureSpec.equalMajor(condition.getMajor()));
		}
		
		return lectureRepository.findAll(spec, pageable);
	}
}

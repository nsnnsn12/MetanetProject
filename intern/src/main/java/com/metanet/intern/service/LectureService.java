package com.metanet.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Lecture;
import com.metanet.intern.repository.LectureRepository;
import com.metanet.intern.vo.LectureListJoin;

@Service
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
}

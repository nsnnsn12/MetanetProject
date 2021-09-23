package com.metanet.intern.service;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.Role;
import com.metanet.intern.repository.ManagerRepository;
import com.metanet.intern.vo.LectureListJoin;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
public class LectureServiceTest {
	@Autowired
	LectureService lectureService;
	@Test
	public void 조회() {
		//Given
		PageRequest pageRequest = PageRequest.of(0, 5);
		
		//When
		Page<LectureListJoin> lectures = lectureService.findAllEducations(pageRequest);
//		Page<Manager> managers = managerService.findAllManagers(pageRequest);
		log.info("현재페이지:"+lectures.getNumber());
//		log.info("페이지크기:"+managers.getSize());
//		log.info("전체 페이지 수:"+managers.getTotalPages());
//		log.info("현재 페이지에 나올 데이터 수:"+managers.getNumberOfElements());
//		log.info("전체 데이터 수:"+managers.getTotalElements());
//		log.info("이전 페이지 여부:"+managers.hasPrevious());
//		log.info("현재 페이지가 첫 페이지인지 여부:"+managers.isFirst());
//		log.info("다음 페이지 여부:"+managers.hasNext());
//		log.info("현재 페이지가 마지막 페이지인지 여부:"+managers.isLast());
		for (LectureListJoin rows : lectures.getContent()) {
			log.info(rows.getProfessorName());
			log.info(rows.getEducationCode());
		}

		//Then
		assertTrue(lectures.getTotalElements() == 1);
	}
}

package com.metanet.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Education;
import com.metanet.intern.repository.EducatinoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EducationService {
	@Autowired
	EducatinoRepository educatinoRepository;

	public void createEducation(Education education) {
		educatinoRepository.save(education);
	}
}

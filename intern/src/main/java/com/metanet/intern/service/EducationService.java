package com.metanet.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.metanet.intern.domain.Education;
import com.metanet.intern.repository.EducationRepository;

@Service
public class EducationService {
	@Autowired
	EducationRepository educationRepository;
	
	final Integer notDeleted = 0;
	
	public void insertEducation(Education education) {
		educationRepository.save(education);
	}
	
	public void updateEducation(Education education) {
		educationRepository.save(education);
	}
	
	public void deleteEducation(Long id) {
		Education education = educationRepository.findById(id).get();
		education.setIsDeleted(1);
		educationRepository.save(education);
	}
	
	public Education findOne(Long id) {
		return educationRepository.getById(id);
	}
	
	public Page<Education> getAllList(Pageable pageable){
		return educationRepository.findByIsDeleted(notDeleted,pageable);
	}
}

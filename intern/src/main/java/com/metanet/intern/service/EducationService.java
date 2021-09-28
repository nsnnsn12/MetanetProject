package com.metanet.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.repository.EducatinoRepository;
import com.metanet.intern.spec.EducationSpec;
import com.metanet.intern.spec.ManagerSpec;
import com.metanet.intern.vo.EducationSearchCondition;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EducationService {
	@Autowired
	EducatinoRepository educatinoRepository;

	private final int delete = 1, notDelete = 0;
	public void createEducation(Education education) {
		educatinoRepository.save(education);
	}

	public Page<Education> searchEducationList(Pageable pageable, EducationSearchCondition condition) {
		//삭제되지 않은 정보
		Specification<Education> spec = Specification.where(EducationSpec.isNotDeleted());
		if(!condition.getType().isBlank() && !condition.getText().isBlank()) {
			if(condition.getType().equals("title")) {
				spec = spec.and(EducationSpec.likeTitle(condition.getText()));
			}else {
				spec = spec.and(EducationSpec.likeCode(condition.getText()));
			}
		}
		
		if(condition.getCredit() != null) {
			spec = spec.and(EducationSpec.equalCredit(condition.getCredit()));
		}
		
		if(condition.getDivision() != null) {
			spec = spec.and(EducationSpec.equalDivision(condition.getDivision()));
		}
		
		if(condition.getMajor() != null) {
			spec = spec.and(EducationSpec.equalMajor(condition.getMajor()));
		}
		
		return educatinoRepository.findAll(spec, pageable);
	}

	public void delete(Long id) {
		educatinoRepository.getById(id).setIsDeleted(delete);
	}
}

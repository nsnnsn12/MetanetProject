package com.metanet.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.metanet.intern.domain.Major;
import com.metanet.intern.repository.MajorRepository;

@Service
public class MajorService {
	@Autowired
	MajorRepository majorRepository;
	
	final Integer notDeleted = 0;

	public void inserMajor(Major major) {
		majorRepository.save(major);
	}
	
	public Major findOne(Long id) {
		return majorRepository.getById(id);
	}

	public Page<Major> getAllList(Pageable pageable) {
		return majorRepository.findByIsDeleted(notDeleted, pageable);
	}

}

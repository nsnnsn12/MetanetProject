package com.metanet.intern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Major;
import com.metanet.intern.repository.MajorRepository;

@Service
@Transactional
public class MajorService {
	@Autowired
	MajorRepository majorRepository;
	
	final Integer notDeleted = 0;

	public void insertMajor(Major major) {
		majorRepository.save(major);
	}
	
	public void updateMajor(Major major) {
		majorRepository.save(major);
	}
	
	public void deleteMajor(Long id) {
		Major major = majorRepository.findById(id).get();
		major.setIsDeleted(1);
		majorRepository.save(major);
	}
	
	public Major findOne(Long id) {
		return majorRepository.getById(id);
	}

	public Page<Major> getAllList(Pageable pageable) {
		return majorRepository.findByIsDeleted(notDeleted, pageable);
	}
	
	public List<Major> getAll(){
		return majorRepository.findByIsDeleted(0);
	}

	

}

package com.metanet.intern.service;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.repository.ManagerRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ManagerService {
	
	@Autowired
	ManagerRepository managerRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Long join(Manager manager) {
		validationDuplicateLoginId(manager);
		manager.setPassword(passwordEncoder.encode(manager.getPassword()));
		managerRepository.save(manager);
		return manager.getId();
	}
	
	public Manager fineOne(Long id) {
		return managerRepository.findById(id).get();
	}
	
	public Long save(Manager manager) {
		managerRepository.save(manager);
		return manager.getId();
	}
	
	public Page<Manager> findManagers(Pageable pageable){
		return managerRepository.findAll(pageable);
	}
	
	private void validationDuplicateLoginId(Manager manager) {
		List<Manager> managers = managerRepository.findByLoginId(manager.getLoginId());
		if(!managers.isEmpty()) {
			throw new IllegalStateException("존재하는 아이디입니다.");
		}
	}
}

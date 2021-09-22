package com.metanet.intern.service;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.Role;
import com.metanet.intern.repository.ManagerRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ManagerServiceImpl implements ManagerService{
	
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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Manager> managers = managerRepository.findByLoginId(username);
		if(managers.isEmpty()) {
			throw new IllegalStateException("아이디가 존재하지 않습니다.");
		}
		Manager manager = managers.get(0);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(manager.getRole() == Role.admin) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if(manager.getRole() == Role.professor) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
        }else if(manager.getRole() == Role.manager) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }else {
        	throw new IllegalStateException("권한이 존재하지 않습니다.");
        }
        
        return new User(manager.getLoginId(), manager.getPassword(), authorities);
	}
	
	private void validationDuplicateLoginId(Manager manager) {
		List<Manager> managers = managerRepository.findByLoginId(manager.getLoginId());
		if(!managers.isEmpty()) {
			throw new IllegalStateException("존재하는 아이디입니다.");
		}
	}
}

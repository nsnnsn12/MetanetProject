package com.metanet.intern.service;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.enummer.Role;
import com.metanet.intern.repository.ManagerRepository;
import com.metanet.intern.repository.PhotoRepository;
import com.metanet.intern.spec.ManagerSpec;
import com.metanet.intern.vo.ManagerSearchCondition;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	PhotoRepository photoRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private final StorageService storageService;
	private final Integer isNotDeleted = 0;

	@Autowired
	public ManagerServiceImpl(StorageService storageService) {
		this.storageService = storageService;
	}
	
	public Long join(Manager manager) {
		validationDuplicateLoginId(manager);
		manager.setPassword(passwordEncoder.encode(manager.getPassword()));
		
		//파일저장
		PhotoFile photoFile = storageService.photoStore(manager.getImage());
		
		if(photoFile != null) {
			photoRepository.save(photoFile);
			manager.setPhoto(photoFile);
		}
		managerRepository.save(manager);
		return manager.getId();
	}
	
	public Manager findOne(Long id) {
		return managerRepository.findById(id).get();
	}
	
	public Long save(Manager manager) {
		managerRepository.save(manager);
		return manager.getId();
	}
	
	@Override
	public void update(Manager tempManager) {
		Manager manager = managerRepository.getById(tempManager.getId());
		manager.setMajor(tempManager.getMajor());
		manager.setRole(tempManager.getRole());
		managerRepository.save(manager);
	}
	
	@Override
	public void mypageUpdate(Manager tempManager) {
		Manager manager = managerRepository.getById(tempManager.getId());
		//파일저장
		PhotoFile photoFile = storageService.photoStore(tempManager.getImage());
		
		if(photoFile != null) {
			photoRepository.save(photoFile);
			manager.setPhoto(photoFile);
		}
		//이름, 이메일, 전화번호, 생년월일
		manager.setName(tempManager.getName());
		manager.setEmail(tempManager.getEmail());
		manager.setTelNo(tempManager.getTelNo());
		manager.setBirth(tempManager.getBirth());
		managerRepository.save(manager);
		
	}
	
	@Override
	public Manager findOne(String loginId) {
		// TODO Auto-generated method stub
		return managerRepository.findByLoginIdAndIsDeleted(loginId, isNotDeleted);
	}
	
	public Page<Manager> findAllManagers(Pageable pageable){
		final Integer isDeleted = 0;
		return managerRepository.findByIsDeleted(isDeleted, pageable);
	}
	
	public Page<Manager> searchManagerList(Pageable pageable, ManagerSearchCondition condition){
		//삭제되지 않은 정보
		Specification<Manager> spec = Specification.where(ManagerSpec.isNotDeleted());
		if(!condition.getType().isBlank() && !condition.getText().isBlank()) {
			if(condition.getType().equals("name")) {
				spec = spec.and(ManagerSpec.likeName(condition.getText()));
			}else {
				spec = spec.and(ManagerSpec.likeLoginId(condition.getText()));
			}
		}
		
		if(condition.getRoleFilter() != null) {
			spec = spec.and(ManagerSpec.equalRole(condition.getRoleFilter()));
		}
		
		if(condition.getAcceptFilter() != null) {
			spec = spec.and(ManagerSpec.equalAccept(condition.getAcceptFilter()));
		}
		return managerRepository.findAll(spec, pageable);
	}
	
	@Override
	public void updateAccept(Long id, Integer acceptFlag) {
		Manager manager = managerRepository.findById(id).get();
		manager.setIsAccept(acceptFlag);
	}
	
	@Override
	public void delete(Long id, Integer deleteFlag) {
		Manager manager = managerRepository.findById(id).get();
		manager.setIsDeleted(deleteFlag);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Manager manager = managerRepository.findByLoginIdAndIsDeleted(username, isNotDeleted);
		if(manager == null) {
			throw new IllegalStateException("아이디가 존재하지 않습니다.");
		}
		isAccept(manager);
		
        List<GrantedAuthority> authorities = new ArrayList<>();
        grantRole(authorities, manager);
        
        return new User(manager.getLoginId(), manager.getPassword(), authorities);
	}
	
	private void isAccept(Manager manager) {
		if(manager.getIsAccept() == 0) {
			throw new IllegalStateException("승인되지 않은 회원정보입니다.");
		}
	}
	
	private void grantRole(List<GrantedAuthority> authorities, Manager manager) {
		if(manager.getRole() == Role.admin) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if(manager.getRole() == Role.professor) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
        }else if(manager.getRole() == Role.manager) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }else {
        	throw new IllegalStateException("권한이 존재하지 않습니다.");
        }
	}
	
	private void validationDuplicateLoginId(Manager manager) {
		List<Manager> managers = managerRepository.findByLoginId(manager.getLoginId());
		if(!managers.isEmpty()) {
			throw new IllegalStateException("존재하는 아이디입니다.");
		}
	}
}

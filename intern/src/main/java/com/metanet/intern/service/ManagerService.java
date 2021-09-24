package com.metanet.intern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.repository.ManagerRepository;

public interface ManagerService extends UserDetailsService{
	public Long join(Manager manager);
	
	public Manager fineOne(Long id);
	
	public Long save(Manager manager);
	
	public Page<Manager> findAllManagers(Pageable pageable);

	public void updateAccept(Long id, Integer acceptFlag);
	public void delete(Long id, Integer deleteFlag);
}

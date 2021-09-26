package com.metanet.intern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.repository.ManagerRepository;
import com.metanet.intern.vo.ManagerSearchCondition;

public interface ManagerService extends UserDetailsService{
	public Long join(Manager manager);
	
	public Manager findOne(Long id);
	public Manager findOne(String loginId);
	public Long save(Manager manager);
	
	public Page<Manager> findAllManagers(Pageable pageable);

	public void updateAccept(Long id, Integer acceptFlag);
	public void delete(Long id, Integer deleteFlag);
	
	public Page<Manager> searchManagerList(Pageable pageable, ManagerSearchCondition condition);

	public void update(Manager manager);
	public void mypageUpdate(Manager manager);
}

package com.metanet.intern.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.repository.ManagerRepository;

@Transactional
public class ManagerServiceTest {
	@Autowired
	ManagerRepository managerRepository;
	
	@Test
	public void join() {
		
	}
}

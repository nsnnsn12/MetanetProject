package com.metanet.intern.service;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.Role;
import com.metanet.intern.service.ManagerService;

@Transactional
@SpringBootTest
public class ManagerServiceTest {
	@Autowired
	ManagerService managerService;
	
	@Test
	public void entityValidation() {
		//Given
		Manager manager = new Manager();
		manager.setLoginId("abcdef");
		//manager.setPassword("1234");
		manager.setName("노성규");
		manager.setRole(Role.manager);
		//When
		managerService.join(manager);
		//Then
	}
	@Test
	public void join() {
		
	}
}

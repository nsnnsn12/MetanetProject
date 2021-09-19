package com.metanet.intern.service;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.Role;
import com.metanet.intern.repository.ManagerRepository;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
public class ManagerServiceTest {
	@Autowired
	ManagerService managerService;
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Test
	public void 회원가입테스트() {
		//Given
		Manager manager = new Manager();
		manager.setLoginId("abcdef");
		manager.setPassword("1234");
		manager.setName("노성규");
		manager.setRole(Role.manager);
		
		//When
		managerService.join(manager);
		log.info("디버스 테스트");
		log.info(manager.toString());
		//Then
		assertEquals(manager, managerRepository.findById(manager.getId()).get());
	}
	
	@Test
	public void 중복아이디테스트() {
		//Given
		Manager manager = new Manager();
		manager.setLoginId("abcdef");
		manager.setPassword("1234");
		manager.setName("노성규");
		manager.setRole(Role.manager);
		
		managerService.join(manager);
		
		//When
		Exception exception = assertThrows(IllegalStateException.class, () ->{
			Manager manager2 = new Manager();
			manager2.setLoginId("abcdef");
			manager2.setPassword("1234");
			manager2.setName("노성규");
			manager2.setRole(Role.manager);
			managerService.join(manager2);
		});
		
		//Then
		String expectedMessage = "존재하는 아이디입니다.";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
}

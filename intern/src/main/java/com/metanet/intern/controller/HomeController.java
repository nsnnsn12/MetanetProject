package com.metanet.intern.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.service.ManagerService;
import com.metanet.intern.spec.ManagerSpec;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Autowired
	ManagerService managerService;
	
	@GetMapping("/index")
	public String index() {
		return "thymeleaf/index";
	}	
	@GetMapping(value = {"/", "/login"})
	public String longinView() {
		if (isAuthenticated()) {
	        return "redirect:index";
	    }
		return "thymeleaf/account/login";
	}
	
	@GetMapping("/mypage")
	public String mypage(Model model, Principal principal) {
		log.info(principal.getName());
		log.info(managerService.findOne(principal.getName()).toString());
		model.addAttribute("manager", managerService.findOne(principal.getName()));
		return "thymeleaf/account/mypage";
	}
	
	@PostMapping("/update")
	public String update(Manager manager) {
		log.info(manager.toString());
		managerService.mypageUpdate(manager);
		
		return "redirect:mypage";
	}
	
	@GetMapping("/deniedPage")
	public String denied() {
		return "thymeleaf/404page";
	}
	
	private boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.
	      isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}
}

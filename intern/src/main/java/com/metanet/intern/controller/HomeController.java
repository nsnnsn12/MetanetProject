package com.metanet.intern.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@GetMapping("/index")
	public String index() {
		return "thymeleaf/index";
	}	
	@GetMapping(value = {"/", "/login"})
	public String longinView() {
		if (isAuthenticated()) {
	        return "index";
	    }
		return "thymeleaf/account/login";
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

package com.metanet.intern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@GetMapping("/index")
	public String index() {
		return "thymeleaf/index";
	}	
	@GetMapping("/")
	public String longinView() {
		return "thymeleaf/account/login";
	}
	
	@GetMapping("/deniedPage")
	public String denied() {
		return "thymeleaf/404page";
	}
}

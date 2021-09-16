package com.metanet.intern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/education")
public class EducationController {
	@GetMapping("list")
	public String list() {
		return "thymeleaf/education/education_list";
	}
	
	@GetMapping("detail")
	public String detail() {
		return "thymeleaf/education/education_modify";
	}
	
	@GetMapping("create")
	public String create() {
		return "thymeleaf/education/education_create";
	}
}

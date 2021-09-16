package com.metanet.intern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/education")
public class EducationController {
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("header", "교과목관리");
		return "thymeleaf/education/education_list";
	}
}

package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.metanet.intern.domain.Education;
import com.metanet.intern.enummer.EducationDivision;
import com.metanet.intern.service.EducationService;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/education")
public class EducationController {
	@Autowired
	EducationService educationService;
	
	@Autowired
	MajorService majorService;
	
	EducationDivision division;

	@GetMapping("list")
	public String list() {
		return "thymeleaf/education/education_list";
	}

	@GetMapping("detail")
	public String detail() {
		return "thymeleaf/education/education_modify";
	}

	@GetMapping("create")
	public String create( Model model, Education education) {
		model.addAttribute("majorList", majorService.getAll());
		model.addAttribute("divisionList",division);
		return "thymeleaf/education/education_create";
	}

	@PostMapping("create")
	public String insertEducation(Education education) {
		educationService.insertEducation(education);
		log.info(education.getName());
		return "redirect:list";
	}

	@GetMapping("open/list")
	public String openList() {
		return "thymeleaf/education/open_list";
	}

	@GetMapping("open/create")
	public String openCreate() {
		return "thymeleaf/education/open_create";
	}

	@GetMapping("open/modify")
	public String openModify() {
		return "thymeleaf/education/open_modify";
	}
}

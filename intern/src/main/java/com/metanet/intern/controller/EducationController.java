package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.service.EducationService;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.vo.EducationSearchCondition;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/education")
public class EducationController {
	@Autowired
	EducationService educationService;
	
	@Autowired
	MajorService majorService;
	
	private Pager pager;
	private final int pageGroupSize = 5;
	
	@GetMapping("list")
	public String list(@ModelAttribute("condition") EducationSearchCondition condition, Pageable pageable, Model model) {
		log.info(condition.toString());
		paging(condition, model, pageable);
		return "thymeleaf/education/education_list";
	}

	@GetMapping("page/{pageNo}")
	public String search(@ModelAttribute("condition") EducationSearchCondition condition, @PathVariable("pageNo")int pageNo, Model model) {
		Pageable pageable = PageRequest.of(pageNo, 10);
		paging(condition, model, pageable);
		return "thymeleaf/education/education_list";
	}
	
	private void paging(EducationSearchCondition condition, Model model, Pageable pageable) {
		model.addAttribute("majorList", majorService.getAll());
		Page<Education> page = educationService.searchEducationList(pageable, condition);
		model.addAttribute("page", page);
		pager = new Pager(page.getSize(), pageGroupSize, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
	}
	
	@GetMapping("detail")
	public String detail() {
		return "thymeleaf/education/education_modify";
	}

	@GetMapping("create")
	public String createForm(Education education, Model model) {
		model.addAttribute("majorList", majorService.getAll());
		return "thymeleaf/education/education_create";
	}
	
	@PostMapping("create")
	public String create(Education education) {
		log.info(education.getContent());
		educationService.createEducation(education);
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

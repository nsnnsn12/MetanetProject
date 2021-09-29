package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String list(@ModelAttribute("condition") EducationSearchCondition condition,Pageable pageable, Model model) {
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
	
	@GetMapping("detail/{id}")
	public String detail(@PathVariable("id")Education education, Model model) {
		model.addAttribute("education", education);
		model.addAttribute("majorList", majorService.getAll());
		return "thymeleaf/education/education_create";
	}
	
	@GetMapping("create")
	public String createForm(Education education, Model model) {
		model.addAttribute("majorList", majorService.getAll());
		return "thymeleaf/education/education_create";
	}
	
	@PostMapping("create")
	public String create(Education education) {
		log.info(education.toString());
		educationService.createEducation(education);
		return "redirect:list";
	}

	@GetMapping("delete")
	public String deleteManager(Long id) {
		educationService.delete(id);
		return "redirect:list";
	}
//	
//	@GetMapping("educationNo/{id}")
//	@ResponseBody
//	public Education getEducaiton(@PathVariable("id")Education education, Model model) {
//		education.setProfessors(educationService.test(education));
//		return education;
//	}
	
	
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

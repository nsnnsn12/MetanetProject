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
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Lecture;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.service.EducationService;
import com.metanet.intern.service.LectureService;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.vo.EducationSearchCondition;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/class")
public class ClassController {
	@Autowired
	EducationService educationService;
	
	@Autowired
	MajorService majorService;
	
	@Autowired
	LectureService lectureService;
	
	private Pager pager;
	private final int pageGroupSize = 5;
	
	@ModelAttribute("lecture")
	public Lecture setLecture() {
		return new Lecture();
	}
	
	@GetMapping("create/{id}")
	public String createForm(@PathVariable("id")Education education, Model model) {
		model.addAttribute("education", education);
		model.addAttribute("professors",educationService.getProfessor(education));
		return "thymeleaf/lecture/lecture_modify";
	}
	
	@PostMapping("create")
	public String create(Lecture lecture) {
		log.info(lecture.getId().toString());
		lectureService.create(lecture);
		return "redirect:/lecture/list";
	}
}

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
import com.metanet.intern.domain.Lecture;
import com.metanet.intern.service.EducationService;
import com.metanet.intern.service.LectureService;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.vo.EducationSearchCondition;
import com.metanet.intern.vo.LectureSearchCondition;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/lecture")
public class LectureController {
	@Autowired
	EducationService educationService;
	@Autowired
	LectureService lectureService;
	@Autowired
	MajorService majorService;
	
	private Pager pager;
	private final int pageGroupSize = 5;
	@ModelAttribute("lecture")
	public Lecture setLecture() {
		return new Lecture();
	}
	
	@GetMapping("list")
	public String list(@ModelAttribute("condition") LectureSearchCondition condition, Pageable pageable, Model model) {
		log.info(condition.toString());
		paging(condition, model, pageable);
		return "thymeleaf/lecture/lecture_list";
	}

	@GetMapping("page/{pageNo}")
	public String search(@ModelAttribute("condition") LectureSearchCondition condition, @PathVariable("pageNo")int pageNo, Model model) {
		Pageable pageable = PageRequest.of(pageNo, 10);
		paging(condition, model, pageable);
		return "thymeleaf/lecture/lecture_list";
	}
	
	private void paging(LectureSearchCondition condition, Model model, Pageable pageable) {
		model.addAttribute("majorList", majorService.getAll());
		Page<Lecture> page = lectureService.searchLectureList(pageable, condition);
		for(Lecture lecture : page.getContent()) {
			log.info(lecture.getEducation().getTitle());
		}
		model.addAttribute("page", page);
		pager = new Pager(page.getSize(), pageGroupSize, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
	}
	
	@GetMapping("create/{id}")
	public String createForm(@PathVariable("id")Education education, Model model) {
		model.addAttribute("education", education);
		model.addAttribute("professors",educationService.getProfessor(education));
		return "thymeleaf/lecture/lecture_modify";
	}
	
	@PostMapping("create")
	public String create(Lecture lecture) {
		log.info(lecture.toString());
		lectureService.create(lecture);
		return "redirect:/lecture/list";
	}
	
	@GetMapping("modify/{id}")
	public String modify(@PathVariable("id")Lecture lecture, Model model) {
		model.addAttribute("lecture", lecture);
		model.addAttribute("education", lecture.getEducation());
		model.addAttribute("professors",educationService.getProfessor(lecture.getEducation()));
		return "thymeleaf/lecture/lecture_modify";
	}
}

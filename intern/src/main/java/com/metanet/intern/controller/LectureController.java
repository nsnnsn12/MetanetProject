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
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Lecture;
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
	LectureService lectureService;
	@Autowired
	MajorService majorService;
	
	private Pager pager;
	private final int pageGroupSize = 5;
	
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
	
	@GetMapping("student_detail")
	public String student_detail() {
		return "thymeleaf/lecture/student/student_detail";
	}

	@GetMapping("attendance_search")
	public String attendance_search() {
		return "thymeleaf/lecture/attendance/attendance_search";
	}

	@GetMapping("attendance_detail")
	public String attendance_detail() {
		return "thymeleaf/lecture/attendance/attendance_detail";
	}

	@GetMapping("attendance_modify")
	public String attendance_modify() {
		return "thymeleaf/lecture/attendance/attendance_modify";
	}

	@GetMapping("grade_search")
	public String grade_search() {
		return "thymeleaf/lecture/grade/grade_search";
	}

	@GetMapping("grade_detail")
	public String grade_detail() {
		return "thymeleaf/lecture/grade/grade_detail";
	}

	@GetMapping("grade_create")
	public String grade_create() {
		return "thymeleaf/lecture/grade/grade_create";
	}

	@GetMapping("grade_modify")
	public String grade_modify() {
		return "thymeleaf/lecture/grade/grade_modify";
	}
}

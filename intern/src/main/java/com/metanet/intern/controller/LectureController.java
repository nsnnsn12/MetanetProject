package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Lecture;
import com.metanet.intern.service.LectureService;
import com.metanet.intern.vo.Pager;

@Controller
@RequestMapping("/lecture")
public class LectureController {
	@Autowired
	LectureService lectureService;
	
	@GetMapping("student_search")
	public String student_search() {
		return "thymeleaf/lecture/student/student_search";
	}

	@GetMapping("list")
	public String educationList(Pageable pageable, Model model) {
		//Page<Education> page = lectureService.findAllEducations(pageable);
		//model.addAttribute("educationList", page.getContent());
		//model.addAttribute("page", page);
		//Pager pager = new Pager(page.getSize(),5,(int)page.getTotalElements(),page.getNumber());
		return "thymeleaf/lecture/student_search";
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

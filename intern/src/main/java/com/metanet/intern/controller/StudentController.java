package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.Student;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired

	// 학적 목록조회
	@GetMapping("list")
	public String list() {
		return "/thymeleaf/student/student_list";
	}

	// 학적 상세조회
	@GetMapping("detail")
	public String detail() {
		return "/thymeleaf/student/student_detail";
	}

	// 학적 등록
	@GetMapping("join")
	public String joinForm(Model model) {
		model.addAttribute("majorList", null);
		return "/thymeleaf/student/student_join";
	}

	//학적 등록 처리
	@PostMapping("join")
	public String join(Student student) {
		return "redirect:joinSuccess";
	}

	// 학적 수정
	@GetMapping("modify")
	public String modify() {
		return "/thymeleaf/student/student_modify";
	}

}

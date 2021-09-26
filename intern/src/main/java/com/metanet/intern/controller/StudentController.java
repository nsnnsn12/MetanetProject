package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.domain.Student;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.service.StudentService;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@Autowired
	MajorService majorService;

	// 학적 목록조회
	@GetMapping("list")
	public String list(Pageable pageable, Model model) {
		Page<Student> page = studentService.list(pageable);
		model.addAttribute("studentList", page.getContent());
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);	
		
		return "/thymeleaf/student/student_list";
	}

	// 학적 상세조회
	@GetMapping("detail/{id}")
	public String detail(@PathVariable("id") Student student, Model model) {
		preventPhotoNull(student);
		model.addAttribute("student", student);
		return "/thymeleaf/student/student_detail";
	}
	
	//사진유무 검사
	public void preventPhotoNull(Student student) {
		if(student.getPhoto() == null) {
			student.setPhoto(new PhotoFile());
			student.getPhoto().setId((long)0);
		}
	}

	// 학적 등록
	@GetMapping("join")
	public String joinForm(Model model, Student student) {
		model.addAttribute("majorList", majorService.getAll());
		return "/thymeleaf/student/student_join";
	}

	// 학적 등록 처리
	@PostMapping("join")
	public String join(Student student) {
		if (studentService.join(student) == null)
			return "/thymeleaf/404page";
		return "redirect:joinSuccess";
	}

	// 학적 등록 성공
	@GetMapping("joinSuccess")
	public String joinSuccess() {
		return "thymeleaf/student/student_join_success";
	}

	// 학적 수정
	@GetMapping("modify/{id}")
	public String modifyForm(@PathVariable("id") Student student, Model model) {
		model.addAttribute("majorList", majorService.getAll());
		model.addAttribute("student", student);
		return "/thymeleaf/student/student_modify";
	}
	
	// 학적 수정 처리
		@PostMapping("modify")
		public String modify(Student student) {
			if (studentService.modify(student) == null)
				return "/thymeleaf/404page";
			return "redirect:joinSuccess";
		}

}

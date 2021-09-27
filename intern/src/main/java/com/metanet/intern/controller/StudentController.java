package com.metanet.intern.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.domain.Student;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.service.StudentService;
import com.metanet.intern.vo.ManagerSearchCondition;
import com.metanet.intern.vo.Pager;
import com.metanet.intern.vo.StudentSearchCondition;

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
	public String list(@ModelAttribute("condition") StudentSearchCondition condition, Pageable pageable, Model model) {
		Page<Student> page = studentService.searchList(pageable, condition);
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int) page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
		model.addAttribute("majorList", majorService.getAll());

		return "/thymeleaf/student/student_list";
	}

	// 학적 상세조회
	@GetMapping("detail/{id}")
	public String detail(@PathVariable("id") Student student, Model model) {
		preventPhotoNull(student);
		model.addAttribute("student", student);
		return "/thymeleaf/student/student_detail";
	}

	// 사진유무 검사
	public void preventPhotoNull(Student student) {
		if (student.getPhoto() == null) {
			student.setPhoto(new PhotoFile());
			student.getPhoto().setId((long) 0);
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
		preventPhotoNull(student);
		model.addAttribute("majorList", majorService.getAll());
		model.addAttribute("student", student);
		return "/thymeleaf/student/student_modify";
	}

	// 학적 수정 처리
	@ResponseBody
	@PostMapping("modify")
	public String modify(HttpServletResponse response, Student student) throws Exception{
		if (studentService.modify(student) == null)
			return "/thymeleaf/404page";
		return "<script>alert('수정되었습니다.');location.href='/student/detail/"+student.getId()+"'</script>";
	}
	
	//학적 삭제
	@ResponseBody
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Student student) {
		studentService.delete(student);
		return "<script>alert('삭제되었습니다.');location.href='/student/list"+"'</script>";
	}

}

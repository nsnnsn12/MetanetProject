package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.domain.Student;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.service.StudentService;
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

	private Pager pager;
	private final int pageGroupSize = 5;
	private final int pageSize = 3;
	
	// 학적 목록조회
	@GetMapping("list")
	public String list(@ModelAttribute("condition") StudentSearchCondition condition, @PageableDefault(sort = {"createDate"}, direction = Direction.DESC, size = pageSize)Pageable pageable, Model model) {
		paging(condition, model, pageable);
		return "/thymeleaf/student/student_list";
	}
	
	@GetMapping("page/{pageNo}")
	public String search(@ModelAttribute("condition") StudentSearchCondition condition, @PathVariable("pageNo")int pageNo, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, "createDate"));
		paging(condition, model, pageable);
		return "/thymeleaf/student/student_list";
	}
	
	private void paging(StudentSearchCondition condition, Model model, Pageable pageable) {
		Page<Student> page = studentService.searchList(pageable, condition);
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int) page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
		model.addAttribute("majorList", majorService.getAll());
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
	@ResponseBody
	@PostMapping("join")
	public String join(Student student) {
		if (studentService.join(student) == null)
			return "/thymeleaf/404page";
		return "<script>alert('등록되었습니다.');location.href='/student/list'</script>";
	}

	// 학적 수정
	@GetMapping("modify/{id}")
	public String modifyForm(@PathVariable("id") Student student, Model model) {
		//preventPhotoNull(student);
		model.addAttribute("majorList", majorService.getAll());
		model.addAttribute("student", student);
		return "/thymeleaf/student/student_modify";
	}

	// 학적 수정 처리
	@ResponseBody
	@PostMapping("modify")
	public String modify(Student student) throws Exception{
		if (studentService.modify(student) == null)
			return "/thymeleaf/404page";
		return "<script>alert('수정되었습니다.');location.href='/student/detail/"+student.getId()+"'</script>";
	}
	
	//학적 삭제
	@ResponseBody
	@GetMapping("delete")
	public String delete(Long id) {
		studentService.delete(id);
		return "<script>alert('삭제되었습니다.');location.href='/student/list"+"'</script>";
	}

}

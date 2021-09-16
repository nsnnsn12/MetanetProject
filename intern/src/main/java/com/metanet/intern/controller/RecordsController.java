package com.metanet.intern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/records")
public class RecordsController {
	
	//학적 목록조회
	@GetMapping("list")
	public String list() {
		return "/thymeleaf/records/records_list";
	}
	
	//학적 상세조회
	@GetMapping("detail")
	public String detail() {
		return "/thymeleaf/records/records_detail";
	}
	
	//학적 등록
	@GetMapping("create")
	public String enroll() {
		return "/thymeleaf/records/records_create";
	}

	//학적 수정
	@GetMapping("modify")
	public String modify() {
		return "/thymeleaf/records/records_modify";
	}
	
}

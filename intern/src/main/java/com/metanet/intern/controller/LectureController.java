package com.metanet.intern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lecture")
public class LectureController {
	@GetMapping("list")
	public String list() {
		return "thymeleaf/lecture/lecture_list";
	}
}

package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/major")
public class MajorController {
	@Autowired
	MajorService majorService;
	
	@GetMapping("create")
	public String majorInsertForm(Major major){
		return "thymeleaf/major/major_create";
	}
	
	@PostMapping("create")
	public String inserMajor(Major major) {
		majorService.inserMajor(major);
		log.info(major.getTitle());
		return "redirect:list";
	}
	
	@GetMapping("list")
	public String list(Pageable pageable, Model model) {
		Page<Major> page = majorService.getAllList(pageable);
		model.addAttribute("majorList", page.getContent());
		for(Major major : page.getContent()) {
			log.info(major.getTitle());
			log.info(major.getCode());
		}
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
		
		return "thymeleaf/major/major_list";
	}
	
	@GetMapping("detail/{id}")
	public String majorDetailForm(@PathVariable("id")Major major, Model model){
		model.addAttribute("detailObject", major);
		return "thymeleaf/major/major_detail";
	}
	
	@GetMapping("modify/{code}")
	public String majorModifyForm(@PathVariable("code")Major major, Model model){
		
		model.addAttribute("detailObject", major);
		return "thymeleaf/major/major_modify";
	}
}

package com.metanet.intern.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.service.ManagerService;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/account")
public class AccountController {
	@Autowired
	ManagerService managerService;

	@GetMapping("join")
	public String joinForm(Manager manager) {
		return "thymeleaf/account/register";
	}

	@PostMapping("join")
	public String regist(Manager manager) {
		managerService.join(manager);
		log.info(manager.toString());
		return "redirect:joinSuccess";
	}

	@GetMapping("joinSuccess")
	public String joinSuccess() {
		return "thymeleaf/account/join_success";
	}
	
	@GetMapping("joinFail")
	public String joinFail(Model model) {
		return "thymeleaf/account/join_fail";
	}
	
	@RequestMapping("loginFail")
	public String loginFail(Model model) {
		return "thymeleaf/account/login_fail";
	}

	
	@GetMapping("list")
	public String accountList(@PageableDefault(size = 5)Pageable pageable, Model model) {
		Page<Manager> page = managerService.findAllManagers(pageable);
		model.addAttribute("managerList", page.getContent());
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
		return "thymeleaf/account/account_list";
	}
	
	@GetMapping("detail")
	public String accountDetail() {
		return"thymeleaf/account/account_detail";
	}

	@ExceptionHandler(IllegalStateException.class)
	public String illegalEx(IllegalStateException e, RedirectAttributes attributes) {
		log.info(e.getMessage());
		attributes.addFlashAttribute("errMessage", e.getMessage());
		return "redirect:joinFail";
	}
}
